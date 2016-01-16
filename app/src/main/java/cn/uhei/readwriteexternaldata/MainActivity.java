package cn.uhei.readwriteexternaldata;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

/**
 * 读写SD卡数据
 * 须要配置权限
 *<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
 *<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>
 */
public class MainActivity extends AppCompatActivity {

    private TextView show;
    private EditText et;

    //1 获取sdcard路径
    File sdcard = Environment.getExternalStorageDirectory();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et = (EditText) findViewById(R.id.et);
        show = (TextView) findViewById(R.id.show);

        findViewById(R.id.btnWrite).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //2 新建一个文件
                //arg: 存储路径| 文件名称
                File myfile = new File(sdcard,"myfile.txt");

                //3 判断是否当前手机是否有sdcard
                if(!sdcard.exists()){
                    Toast.makeText(getApplicationContext(),"找不到SD卡",Toast.LENGTH_SHORT).show();

                    //跳出函数的执行
                    return;
                }

                try {
                    //创建文件
                    myfile.createNewFile();
                    Toast.makeText(getApplicationContext(),"文件创建完成",Toast.LENGTH_SHORT).show();

                    //用字节流打开创建的文件
                    FileOutputStream fos = new FileOutputStream(myfile);

                    //字节流转为字符流
                    OutputStreamWriter osw = new OutputStreamWriter(fos,"UTF-8");

                    //向文件写入内容 ，from EditView
                    osw.write(et.getText().toString());

                    //刷新
                    osw.flush();
                    //关闭
                    osw.close();
                    fos.close();

                    Toast.makeText(getApplicationContext(),"文件写入完成",Toast.LENGTH_SHORT).show();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });


        findViewById(R.id.btnRead).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //File对象
                File myfile = new File(sdcard,"myfile.txt");

                if(myfile.exists()){

                    try {

                        FileInputStream fis = new FileInputStream(myfile);
                        InputStreamReader isr = new InputStreamReader(fis,"UTF-8");

                        char[] input = new char[fis.available()];

                        //把数据读到input 数组中
                        isr.read(input);

                        isr.close();
                        fis.close();

                        String inString  = new String(input);
                        show.setText(inString);


                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }


            }
        });
    }
}
