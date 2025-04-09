package hust.cs.javacourse.search.index.impl;

import hust.cs.javacourse.search.index.AbstractPostingList;
import hust.cs.javacourse.search.index.AbstractPosting;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class PostingList extends AbstractPostingList {
    /**
     * 添加Posting,要求不能有内容重复的posting
     * @param posting：Posting对象
     */
    @Override
    public void add(AbstractPosting posting) {
        if (indexOf(posting) == -1) {
            list.add(posting);
        }
    }

    /**
     * 获得PosingList的字符串表示
     * @return ： PosingList的字符串表示
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("PostingList{");
        for (AbstractPosting posting : list) {
            sb.append(posting.toString()).append(", ");
        }
        sb.delete(sb.length() - 2, sb.length());
        sb.append('}');
        return sb.toString();
    }

    /**
     * 添加Posting列表,要求不能有内容重复的posting
     * @param postings：Posting列表
     */
    @Override
    public void add(List<AbstractPosting> postings) {
        for (AbstractPosting posting : postings) {
            add(posting);
        }
    }

    /**
     * 返回指定下标位置的Posting
     * @param index ：下标
     * @return： 指定下标位置的Posting
     */
    @Override
    public AbstractPosting get(int index) {
        if (index < 0 || index >= list.size()) {
            return null;
        }
        return list.get(index);
    }

    /**
     * 返回指定Posting对象的下标
     * @param posting：指定的Posting对象
     * @return ：如果找到返回对应下标；否则返回-1
     */
    @Override
    public int indexOf(AbstractPosting posting) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).equals(posting)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 返回指定文档id的Posting对象的下标
     * @param docId ：文档id
     * @return ：如果找到返回对应下标；否则返回-1
     */
    @Override
    public int indexOf(int docId) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getDocId() == docId) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 是否包含指定Posting对象
     * @param posting： 指定的Posting对象
     * @return : 如果包含返回true，否则返回false
     */
    @Override
    public boolean contains(AbstractPosting posting) {
        return indexOf(posting) != -1;
    }

    /**
     * 删除指定下标的Posting对象
     * @param index：指定的下标
     */
    @Override
    public void remove(int index) {
        if (index < 0 || index >= list.size()) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + list.size());
        }
        list.remove(index);
    }

    /**
     * 删除指定的Posting对象
     * @param posting ：定的Posting对象
     */
    @Override
    public void remove(AbstractPosting posting) {
        int index = indexOf(posting);
        if (index != -1) {
            list.remove(index);
        }
    }

    /**
     * 返回PostingList的大小，即包含的Posting的个数
     * @return ：PostingList的大小
     */
    @Override
    public int size() {
        return list.size();
    }

    /**
     * 清除PostingList
     */
    @Override
    public void clear() {
        list.clear();
    }

    /**
     * PostingList是否为空
     * @return 为空返回true;否则返回false
     */
    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    /**
     * 根据文档id的大小对PostingList进行从小到大的排序
     */
    @Override
    public void sort() {
        list.sort(Comparator.comparingInt(AbstractPosting::getDocId));
    }

    /**
     * 写到二进制文件
     * @param out :输出流对象
     */
    @Override
    public void writeObject(java.io.ObjectOutputStream out) {
        try {
            out.writeInt(list.size());
            for (AbstractPosting posting : list) {
                posting.writeObject(out);
            }
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 从二进制文件读
     * @param in ：输入流对象
     */
    @Override
    public void readObject (java.io.ObjectInputStream in){
        try {
            int size = in.readInt();
            for (int i = 0; i < size; i++) {
                AbstractPosting posting = new Posting();
                posting.readObject(in);
                list.add(posting);
            }
        } catch (java.io.IOException e){
            e.printStackTrace();
        }
    }
}
