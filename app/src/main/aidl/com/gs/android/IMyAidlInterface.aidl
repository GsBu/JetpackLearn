package com.gs.android;

//这里客户端的aidl文件包目录必须与服务器端保持一致，因为在两个项目中，要保证路径一直。
//其实可以在一个项目中写的，只要把server设置成远程服务即可。
interface IMyAidlInterface{
    int add(int a, int b);
}