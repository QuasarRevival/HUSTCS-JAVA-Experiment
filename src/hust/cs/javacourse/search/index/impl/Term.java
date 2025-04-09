package hust.cs.javacourse.search.index.impl;

import hust.cs.javacourse.search.index.AbstractTerm;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Term extends AbstractTerm {
    /**
     * 缺省构造函数
     */
    public Term() {
        super();
    }

    /**
     * 构造函数
     *
     * @param content ：Term内容
     */
    public Term(String content) {
        super(content);
    }

    /**
     * 判断二个Term内容是否相同
     *
     * @param obj ：要比较的另外一个Term
     * @return 如果内容相等返回true，否则返回false
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Term) {
            return this.content.equals(((Term) obj).content);
        }
        return false;
    }

    /**
     * 返回Term的字符串表示
     *
     * @return 字符串
     */
    @Override
    public String toString() {
        return this.content;
    }

    /**
     * 返回Term内容
     *
     * @return Term内容
     */
    @Override
    public String getContent() {
        return this.content;
    }

    /**
     * 设置Term内容
     * @param content：Term的内容
     */
    public void setContent(String content){
        this.content = content;
    }

    /**
     * 比较二个Term的字典序
     *
     * @param o ：要比较的Term
     * @return 如果当前Term在字典序上小于o，返回负数；如果相等返回0；如果大于o，返回正数
     */
    @Override
    public int compareTo(AbstractTerm o) {
        return this.content.compareTo(o.getContent());
    }

    /**
     * 写到二进制文件
     * @param out :输出流对象
     */
    @Override
    public void writeObject(ObjectOutputStream out){
        try {
            out.writeObject(this);
        } catch (IOException e) {
            System.out.println("写入索引文件失败");
            e.printStackTrace();
        }
    }

    /**
     * 从二进制文件读
     * @param in ：输入流对象
     */
    @Override
    public void readObject(ObjectInputStream in){
        try {
            Object obj = in.readObject();
            if (obj instanceof Term term) {
                this.content = term.getContent();
            } else {
                System.out.println("读取索引文件失败");
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("读取索引文件失败");
            e.printStackTrace();
        }
    }
}
