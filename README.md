####banner简介
    banner是基于android ViewPager控件来自定义一个左右滑动demo，可以方便开发者快速的完成banner的实现
    开发者只需要传入装有image id 的链表即可。
            

![github](https://github.com/chenhonggy/banner/blob/master/app/src/main/res/drawable-hdpi/photo.jpg "github")

####如何开始
#####手动安装
        1. 复制layout文件夹下class_banner.xml文件到自己layout目录下面。
        2. 复制drawable下面point_background.xml、point_bg_enable.xml、point_bg_normal.xml到自己drawable目录下
        3. 复制easybanner文件夹下面Banner类到自己工文件中。
                
####开始编码
#####xml文件
        <com.banner.main.easybanner.Banner
        android:id="@+id/banner"
        android:layout_width="fill_parent"
        android:layout_height="112.5dp"
        />      
#####主程序里面
        在主程序的Oncreate方法里面调用initImage方法
        imagelist.add(R.drawable.a);
        imagelist.add(R.drawable.b);
        imagelist.add(R.drawable.c);
        imagelist.add(R.drawable.c);
        imagelist.add(R.drawable.c);
        banner = (Banner)findViewById(R.id.banner);
        banner.initImage(imagelist);        
        

####设置监听
在主程序里面加上下一句话即可：
        
        banner.setItemListenner(new Banner.OnItemListenner() {
            @Override
            public void OnChecked(View v, int position) {
                Toast.makeText(MainActivity.this,position+"",Toast.LENGTH_SHORT).show();
            }
        });     
####最近更新
        
        版本： 1.0
        更新内容： ViewPager实现左滑动的banner
