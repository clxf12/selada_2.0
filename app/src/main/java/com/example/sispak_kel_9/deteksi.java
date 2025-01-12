package com.example.sispak_kel_9;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Html;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sispak_kel_9.ml.Sazara;

import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
public class deteksi extends AppCompatActivity {
    ImageView imageView;
    ImageButton button1, button2;
    int imageSize = 128;
    TextView confidence, solusi, result;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deteksi);
        ImageButton button = (ImageButton) findViewById(R.id.button2);
        this.button2 = button;
        button.setOnClickListener(new View.OnClickListener() { // from class: com.example.imageclassifier.MainActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Intent cameraIntent = new Intent("android.intent.action.PICK", MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                deteksi.this.startActivityForResult(cameraIntent, 1);
            }
        });

        result = findViewById(R.id.hasilprediksi);
        confidence = findViewById(R.id.hasilklasifikasi);
        solusi = findViewById(R.id.solusi);
        imageView = findViewById(R.id.imageView);
        button1 = findViewById(R.id.button1);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (deteksi.this.checkSelfPermission("android.permission.CAMERA") == PackageManager.PERMISSION_GRANTED) {
                    Intent cameraIntent = new Intent("android.media.action.IMAGE_CAPTURE");
                    deteksi.this.startActivityForResult(cameraIntent, 3);
                    return;
                }
                deteksi.this.requestPermissions(new String[]{"android.permission.CAMERA"}, 100);
            }
        });
    }
    public void classifyImage(Bitmap image) {
        try {
            Sazara model = Sazara.newInstance(getApplicationContext());

            int imageSize = 128; // Sesuaikan dengan ukuran input model

            TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, imageSize, imageSize, 3}, DataType.FLOAT32);

            ByteBuffer byteBuffer = ByteBuffer.allocateDirect(imageSize * imageSize * 3 * 4); // 4 bytes per float
            byteBuffer.order(ByteOrder.nativeOrder());

            int[] intValues = new int[imageSize * imageSize];
            image.getPixels(intValues, 0, image.getWidth(), 0, 0, image.getWidth(), image.getHeight());
            int pixel = 0;
            for (int i = 0; i < imageSize; ++i) {
                for (int j = 0; j < imageSize; ++j) {
                    int val = intValues[pixel++];
                    byteBuffer.putFloat(((val >> 16) & 0xFF) * 0.003921569f);
                    byteBuffer.putFloat(((val >> 8) & 0xFF) * 0.003921569f);
                    byteBuffer.putFloat((val & 0xFF) * 0.003921569f);
                }
            }
            inputFeature0.loadBuffer(byteBuffer);
            Sazara.Outputs outputs = model.process(inputFeature0);
            TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();
            float[] confidences = outputFeature0.getFloatArray();

            int maxPos = 0;
            float maxConfidence = 0.0f;
            for (int i = 0; i < confidences.length; ++i) {
                if (confidences[i] > maxConfidence) {
                    maxConfidence = confidences[i];
                    maxPos = i;
                }
            }

            String[] classes = {"Mata_Kodok", "Busuk_Batang", "Busuk_Akar"};

            result.setText(classes[maxPos]);
            if (classes[maxPos].equals("Mata_Kodok")) {
                String more = "Selengkapnya";
                String solutionText = "Pencegahan penyakit mata kodok\n" +
                        "\n" +
                        "1. Gunakan benih berkualitas dan tahan penyakit.\n" +
                        "2. Atur jarak tanam agar sirkulasi udara baik.\n" +
                        "3. Hindari kelembapan berlebih pada daun, terutama pada malam hari.\n" +
                        "4. Terapkan rotasi tanaman untuk mengurangi risiko infeksi ulang.\n" +
                        "5. Gunakan pestisida atau fungisida yang sesuai jika diperlukan.\n" +
                        "6. Jika masalah ini terkait dengan penyakit (bukan serangga), konsultasikan dengan ahli pertanian untuk diagnosa lebih tepat.\n";

                // Underline and make it clickable
                SpannableString content = new SpannableString(solutionText + "\n " + more);

                content.setSpan(new UnderlineSpan(), solutionText.length() + 1, content.length(), 0);
                content.setSpan(new ClickableSpan() {
                    @Override
                    public void onClick(@NonNull View widget) {
                        showExplanationDialog("Busuk Mata",
                                "Pencegahan penyakit Mata Kodok\n" +
                                        "\n" +
                                        "1. Gunakan Benih Berkualitas dan Tahan Penyakit\n" +
                                        "Pastikan benih yang digunakan berkualitas dan tahan terhadap penyakit tertentu untuk mengurangi risiko infeksi.\n" +
                                        "\n" +
                                        "2. Atur Jarak Tanam agar Sirkulasi Udara Baik\n" +
                                        "Menjaga jarak tanam yang tepat dapat mendukung sirkulasi udara yang baik, mengurangi kelembapan berlebih, dan meminimalkan peluang penyakit berkembang.\n" +
                                        "\n" +
                                        "3. Hindari Kelembapan Berlebih pada Daun\n" +
                                        "Cegah kelembapan berlebih pada daun, terutama pada malam hari, yang dapat memfasilitasi pertumbuhan jamur dan patogen lainnya.\n" +
                                        "\n" +
                                        "4. Terapkan Rotasi Tanaman\n" +
                                        "Melakukan rotasi tanaman dapat mengurangi risiko infeksi ulang dengan memutus siklus hidup patogen di tanah.\n" +
                                        "\n" +
                                        "5. Gunakan Pestisida atau Fungisida yang Sesuai\n" +
                                        "Jika diperlukan, gunakan pestisida atau fungisida yang tepat sesuai dengan jenis penyakit atau hama yang menyerang tanaman Anda.\n" +
                                        "\n" +
                                        "6. Konsultasikan dengan Ahli Pertanian\n" +
                                        "Jika masalah ini terkait dengan penyakit (bukan serangga), konsultasikan dengan ahli pertanian untuk diagnosa lebih tepat dan solusi yang lebih spesifik."
                        );
                    }
                }, solutionText.length() + 1, content.length(), 0);

                // Set the styled text to the TextView
                solusi.setText(content);
                solusi.setMovementMethod(LinkMovementMethod.getInstance());
            } else if (classes[maxPos].equals("Busuk_Batang")) {
                String more = "Selengkapnya";
                String solutionText = "Pencegahan penyakit busuk batang\n" +
                        "1. Sanitasi Lahan\n" +
                        "- Bersihkan lahan dari sisa tanaman atau gulma yang dapat menjadi tempat berkembangnya patogen.\n" +
                        "2. Pengaturan Irigasi\n" +
                        "- Hindari overwatering, dan pastikan drainase lahan baik untuk mencegah kelembapan berlebih.\n" +
                        "3. Pemilihan Benih Sehat\n" +
                        "- Gunakan benih berkualitas tinggi yang tahan terhadap penyakit busuk batang.\n" +
                        "4. Penggunaan Mulsa\n" +
                        "- Gunakan mulsa untuk mencegah kontak langsung batang dengan tanah yang mungkin mengandung patogen.\n" +
                        "5. Rotasi Tanaman\n" +
                        "- Hindari menanam selada di lahan yang sama terus-menerus untuk mencegah akumulasi patogen.\n" +
                        "6. Pengendalian Kimia\n" +
                        "- Gunakan fungisida atau bakterisida sesuai anjuran jika serangan cukup parah. Bacillus subtilis dan Trichoderma spp. sering digunakan untuk pengendalian biologis.\n" +
                        "7. Ventilasi yang Baik\n" +
                        "- Tanam dengan jarak cukup agar tanaman tidak terlalu rapat, sehingga sirkulasi udara baik.\n" +
                        "8. Jika masalah terus berlanjut, analisis tanah atau konsultasi dengan ahli pertanian dapat membantu menentukan penyebab spesifiknya.\n";

                // Underline and make it clickable
                SpannableString content = new SpannableString(solutionText + "\n " + more);

                content.setSpan(new UnderlineSpan(), solutionText.length() + 1, content.length(), 0);
                content.setSpan(new ClickableSpan() {
                    @Override
                    public void onClick(@NonNull View widget) {
                        showExplanationDialog("Busuk Batang",
                                "Pencegahan penyakit busuk batang\n\n" +

                                        "1. Sanitasi Lahan:\n" +
                                        "- Pastikan lahan bebas dari sisa tanaman atau gulma yang dapat menjadi tempat berkembangnya patogen penyebab busuk batang. Bersihkan area secara rutin untuk mengurangi sumber penyakit.\n\n" +

                                        "2. Pengaturan Irigasi:\n" +
                                        "- Hindari penyiraman berlebihan (overwatering) yang dapat menyebabkan kelembapan tinggi di sekitar tanaman. Pastikan drainase lahan berjalan dengan baik agar tanah tidak terlalu lembap.\n\n" +

                                        "3. Pemilihan Benih Sehat:\n" +
                                        "- Gunakan benih berkualitas tinggi yang tahan terhadap penyakit busuk batang. Pilih varietas yang sudah terbukti memiliki ketahanan terhadap patogen tertentu.\n\n" +

                                        "4. Penggunaan Mulsa:\n" +
                                        "- Gunakan mulsa di sekitar tanaman untuk menjaga kelembaban tanah dan mencegah kontak langsung antara batang tanaman dengan tanah yang mungkin mengandung patogen.\n\n" +

                                        "5. Rotasi Tanaman:\n" +
                                        "- Hindari menanam selada di lahan yang sama selama berturut-turut. Lakukan rotasi tanaman untuk memutus siklus hidup patogen yang dapat berkembang di tanah.\n\n" +

                                        "6. Pengendalian Kimia:\n" +
                                        "- Gunakan fungisida atau bakterisida yang sesuai jika infeksi sudah cukup parah. Bacillus subtilis dan Trichoderma spp. adalah beberapa agen pengendalian biologis yang bisa digunakan untuk mengendalikan busuk batang.\n\n" +

                                        "7. Ventilasi yang Baik:\n" +
                                        "- Pastikan jarak antar tanaman cukup untuk memastikan sirkulasi udara yang baik. Tanaman yang terlalu rapat dapat meningkatkan kelembapan dan mempermudah penyebaran penyakit.\n\n" +

                                        "Jika masalah terus berlanjut, analisis tanah atau konsultasi dengan ahli pertanian setempat dapat membantu dalam menentukan penyebab spesifik dan solusi yang lebih tepat.");
                    }
                }, solutionText.length() + 1, content.length(), 0);

                // Set the styled text to the TextView
                solusi.setText(content);
                solusi.setMovementMethod(LinkMovementMethod.getInstance());
            } else if (classes[maxPos].equals("Busuk_Akar")) {
                String more = "Selengkapnya";
                String solutionText = "Pencegahan penyakit Busuk Akar\n" +
                        "1. Sanitasi Lahan\n" +
                        "- Bersihkan lahan dari sisa tanaman atau material organik yang terinfeksi.\n" +
                        "2. Media Tanam yang Steril\n" +
                        "- Gunakan media tanam yang steril dan bebas dari patogen.\n" +
                        "3. Atur Drainase dengan Baik\n" +
                        "- Pastikan tanah memiliki drainase yang baik untuk menghindari genangan air.\n" +
                        "4. Pengaturan Penyiraman\n" +
                        "- Jangan menyiram terlalu berlebihan; sesuaikan dengan kebutuhan tanaman.\n" +
                        "5. Rotasi Tanaman\n" +
                        "- Hindari menanam selada di lahan yang sama secara terus-menerus untuk mencegah penumpukan patogen.\n" +
                        "6. Penggunaan Fungisida atau Bakterisida\n" +
                        "- Gunakan produk berbahan aktif seperti metalaxyl atau fosetil-al jika disebabkan oleh jamur. Untuk pendekatan ramah lingkungan, gunakan agen hayati seperti Trichoderma spp..\n" +
                        "7. Pemilihan Benih Tahan Penyakit\n" +
                        "- Gunakan varietas selada yang memiliki ketahanan terhadap penyakit busuk akar.\n" +
                        "8. Ventilasi Baik dan Jarak Tanam Cukup\n" +
                        "- Pastikan tanaman tidak terlalu rapat untuk mengurangi kelembapan di area sekitar tanaman.\n";

                // Underline and make it clickable
                SpannableString content = new SpannableString(solutionText + "\n " + more);

                content.setSpan(new UnderlineSpan(), solutionText.length() + 1, content.length(), 0);
                content.setSpan(new ClickableSpan() {
                    @Override
                    public void onClick(@NonNull View widget) {
                        showExplanationDialog("Busuk Akar",
                                "Pencegahan penyakit Busuk Akar\n\n" +

                                        "1. Sanitasi Lahan:\n" +
                                        "- Pastikan lahan bebas dari sisa tanaman yang terinfeksi atau material organik lainnya yang dapat menjadi tempat berkembangnya patogen penyebab penyakit.\n\n" +

                                        "2. Media Tanam yang Steril:\n" +
                                        "- Gunakan media tanam yang steril, seperti tanah yang bebas dari kontaminasi atau tanah yang sudah disterilkan, untuk mengurangi risiko infeksi sejak awal.\n\n" +

                                        "3. Atur Drainase dengan Baik:\n" +
                                        "- Tanah dengan drainase yang baik penting untuk mencegah genangan air yang dapat meningkatkan risiko penyakit jamur atau bakteri yang berkembang dalam kondisi lembap.\n\n" +

                                        "4. Pengaturan Penyiraman:\n" +
                                        "- Hindari penyiraman berlebihan yang dapat menyebabkan kelembapan yang tinggi di sekitar akar. Sesuaikan penyiraman dengan kebutuhan tanaman untuk menjaga kelembapan tanah yang tepat.\n\n" +

                                        "5. Rotasi Tanaman:\n" +
                                        "- Hindari penanaman selada di lahan yang sama secara terus-menerus. Rotasi tanaman dapat mencegah penumpukan patogen di tanah dan mengurangi risiko infeksi ulang.\n\n" +

                                        "6. Penggunaan Fungisida atau Bakterisida:\n" +
                                        "- Gunakan produk kimia yang sesuai untuk mengendalikan penyakit yang disebabkan oleh jamur atau bakteri. Fungisida berbahan aktif metalaxyl atau fosetil-al dapat digunakan untuk infeksi jamur. Sebagai alternatif ramah lingkungan, Trichoderma spp. adalah agen hayati yang efektif untuk mengendalikan patogen.\n\n" +

                                        "7. Pemilihan Benih Tahan Penyakit:\n" +
                                        "- Pilih varietas selada yang memiliki ketahanan terhadap penyakit busuk akar atau penyakit lainnya yang umum terjadi. Benih yang tahan penyakit dapat mengurangi dampak infeksi pada tanaman.\n\n" +

                                        "8. Ventilasi Baik dan Jarak Tanam Cukup:\n" +
                                        "- Tanam tanaman dengan jarak yang cukup untuk memastikan sirkulasi udara yang baik. Sirkulasi udara yang baik akan mengurangi kelembapan berlebih di sekitar tanaman, yang dapat menghambat perkembangan patogen.\n\n" +

                                        "Dengan menggabungkan beberapa langkah ini, Anda dapat meningkatkan ketahanan tanaman terhadap penyakit dan mengurangi risiko infeksi. Jika masalah terus berlanjut, disarankan untuk berkonsultasi dengan ahli pertanian untuk mendapatkan solusi yang lebih tepat sesuai dengan kondisi tanaman dan lingkungan Anda.");
                    }
                }, solutionText.length() + 1, content.length(), 0);

                // Set the styled text to the TextView
                solusi.setText(content);
                solusi.setMovementMethod(LinkMovementMethod.getInstance());
            }

            String s = "";
            for (int i = 0; i < classes.length; ++i) {
                s = s + String.format("%s: %.1f%%\n", classes[i], confidences[i] * 100.0f);
            }
            confidence.setText(s);

            model.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showExplanationDialog(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // User mengklik tombol OK.
                        dialog.dismiss();
                    }
                })
                .show();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == 3) { // Handle camera result
                Bitmap image = (Bitmap) data.getExtras().get("data");
                int dimension = Math.min(image.getWidth(), image.getHeight());
                Bitmap image2 = ThumbnailUtils.extractThumbnail(image, dimension, dimension);
                this.imageView.setImageBitmap(image2);
                int i = this.imageSize;
                classifyImage(Bitmap.createScaledBitmap(image2, i, i, false));
            } else if (requestCode == 1) { // Handle gallery result
                Uri dat = data.getData();
                if (dat != null) {
                    Bitmap image3 = null;
                    try {
                        image3 = MediaStore.Images.Media.getBitmap(getContentResolver(), dat);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (image3 != null) {
                        this.imageView.setImageBitmap(image3);
                        int i2 = this.imageSize;
                        classifyImage(Bitmap.createScaledBitmap(image3, i2, i2, false));
                    }
                }
            }
        } else if (resultCode == RESULT_CANCELED) {
            Intent backIntent = new Intent(this, deteksi.class);
            startActivity(backIntent);
            finish(); // Optional: finish the current activity
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


}
