package com.example.sispak_kel_9;

import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AlertDialog;
public class daftar_penyakit extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_penyakit);
        ImageView anthracnoseImageView = findViewById(R.id.mata_kodok);
        anthracnoseImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showExplanationDialog("Mata Kodok",
                        "Hama mata kodok pada tanaman selada adalah jenis serangga atau penyakit yang dapat menyebabkan kerusakan pada daun selada. Berikut adalah ciri-ciri yang umum terkait hama ini:\n\n" +
                                "1. Bercak Berwarna Coklat atau Hitam:\n" +
                                "- Daun selada akan menunjukkan bercak kecil berwarna coklat atau hitam, mirip dengan mata kodok.\n\n" +
                                "2. Luka pada Daun:\n" +
                                "- Bercak biasanya berkembang menjadi luka yang membentuk lingkaran atau area yang rusak, membuat daun tampak tidak sehat.\n\n" +
                                "3. Daun Menguning atau Kering:\n" +
                                "- Daun yang terinfeksi dapat menguning, kering, atau bahkan rontok jika infeksi terus berlanjut.\n\n" +
                                "4. Penyebaran Cepat:\n" +
                                "- Hama atau penyakit ini menyebar dengan cepat di lingkungan yang lembap, terutama jika jarak tanam terlalu rapat.\n\n" +
                                "5. Penurunan Kualitas Tanaman:\n" +
                                "- Tanaman selada yang terinfeksi akan mengalami pertumbuhan terhambat dan kehilangan kualitas hasil panen.\n\n" +
                                "6. Kehadiran Serangga Kecil (jika terkait hama):\n" +
                                "- Pada beberapa kasus, hama seperti kutu daun atau trips juga dapat meninggalkan bercak mirip 'mata kodok'.");
            }

        });
        ImageView downyMildewImageView = findViewById(R.id.busuk_batang);
        downyMildewImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showExplanationDialog("Busuk Batang",
                        "Hama atau penyakit busuk batang pada tanaman selada biasanya disebabkan oleh infeksi jamur, bakteri, atau serangga yang merusak batang. Berikut adalah ciri-ciri umum yang dapat dikenali:\n\n" +
                                "Ciri-Ciri Busuk Batang pada Tanaman Selada:\n\n" +
                                "1. Batang Menghitam atau Coklat Gelap:\n" +
                                "- Terlihat perubahan warna pada batang, terutama di bagian pangkal dekat tanah. Warnanya menjadi coklat gelap atau hitam.\n\n" +
                                "2. Jaringan Batang Lunak dan Lembek:\n" +
                                "- Bagian batang yang terinfeksi menjadi lunak, berlendir, dan mudah hancur saat disentuh.\n\n" +
                                "3. Kehadiran Bau Tidak Sedap:\n" +
                                "- Pada infeksi bakteri, area yang membusuk sering mengeluarkan bau busuk akibat dekomposisi jaringan.\n\n" +
                                "4. Batang Pecah atau Robek:\n" +
                                "- Infeksi yang parah dapat menyebabkan batang retak, pecah, atau bahkan putus, sehingga tanaman tidak dapat berdiri tegak.\n\n" +
                                "5. Daun Layu dan Menguning:\n" +
                                "- Daun tanaman selada akan mulai layu, menguning, dan akhirnya mati karena suplai nutrisi terganggu akibat kerusakan pada batang.\n\n" +
                                "6. Pertumbuhan Tanaman Terhenti:\n" +
                                "- Tanaman terlihat kerdil atau tidak tumbuh dengan baik karena serangan busuk batang.\n\n" +
                                "7. Kehadiran Jamur atau Spora:\n" +
                                "- Kadang ditemukan lapisan putih, abu-abu, atau hitam berupa spora jamur di area batang yang terinfeksi.\n\n" +
                                "8. Lingkungan Lembap dan Basah:\n" +
                                "- Busuk batang sering muncul di kondisi lingkungan yang terlalu lembap atau memiliki drainase buruk.");
            }
        });

        ImageView mosaicVirusImageView = findViewById(R.id.busuk_akar);
        mosaicVirusImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showExplanationDialog("Busuk Akar",
                        "Hama atau penyakit busuk akar pada tanaman selada sering disebabkan oleh infeksi jamur atau bakteri di bagian akar. Berikut adalah ciri-ciri umum yang dapat dikenali:\n\n" +
                                "Ciri-Ciri Busuk Akar pada Tanaman Selada:\n\n" +
                                "1. Akar Berwarna Coklat atau Hitam:\n" +
                                "- Akar yang sehat biasanya berwarna putih. Pada tanaman yang terkena busuk akar, warna akar berubah menjadi coklat tua hingga hitam.\n\n" +
                                "2. Akar Membusuk dan Lunak:\n" +
                                "- Jaringan akar terlihat membusuk, lunak, dan mudah hancur saat disentuh.\n\n" +
                                "3. Akar Mengeluarkan Bau Busuk:\n" +
                                "- Infeksi bakteri sering menyebabkan akar mengeluarkan bau tidak sedap akibat proses dekomposisi.\n\n" +
                                "4. Pertumbuhan Terhambat:\n" +
                                "- Tanaman selada yang terserang akan terlihat kerdil karena penyerapan nutrisi terganggu.\n\n" +
                                "5. Daun Menguning dan Layu:\n" +
                                "- Daun mulai menguning, layu, dan akhirnya mati meskipun penyiraman cukup karena akar tidak bisa menyuplai air dan nutrisi.\n\n" +
                                "6. Tanaman Mudah Tercabut:\n" +
                                "- Karena akar membusuk, tanaman menjadi tidak kokoh dan mudah tercabut dari tanah.\n\n" +
                                "7. Lingkungan Lembap atau Drainase Buruk:\n" +
                                "- Busuk akar sering muncul pada kondisi tanah yang terlalu basah atau kurang drainase.\n\n" +
                                "8. Kehadiran Jamur atau Patogen di Akar:\n" +
                                "- Kadang terlihat spora jamur berwarna putih, abu-abu, atau coklat di sekitar akar atau pangkal batang.\n\n" +
                                "Penyebab Utama Busuk Akar:\n" +
                                "- Infeksi jamur seperti Pythium spp., Rhizoctonia spp., atau Fusarium spp.\n" +
                                "- Infeksi bakteri patogen tertentu.\n" +
                                "- Kondisi tanah yang terlalu lembap dan kekurangan oksigen.\n" +
                                "- Drainase buruk yang menyebabkan genangan air di sekitar akar.\n" +
                                "- Penanaman terlalu rapat yang memicu kelembapan tinggi.");
            }
        });
    }
    private void showExplanationDialog(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialogCustom);
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
}