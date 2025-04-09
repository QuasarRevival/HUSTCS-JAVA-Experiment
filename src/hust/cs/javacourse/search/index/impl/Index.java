package hust.cs.javacourse.search.index.impl;

import hust.cs.javacourse.search.index.*;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * AbstractIndex的具体实现类
 */
public class Index extends AbstractIndex {
    /**
     * 返回索引的字符串表示
     *
     * @return 索引的字符串表示
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Index{");
        sb.append("docIdToDocPathMapping=").append(docIdToDocPathMapping);
        sb.append(", termToPostingListMapping=").append(termToPostingListMapping);
        sb.append('}');
        return sb.toString();
    }

    /**
     * 添加文档到索引，更新索引内部的HashMap
     *
     * @param document ：文档的AbstractDocument子类型表示
     */
    @Override
    public void addDocument(AbstractDocument document) {
        this.docIdToDocPathMapping.put(document.getDocId(), document.getDocPath());
        List<AbstractTermTuple> tuples = document.getTuples();
        AbstractPostingList postingList;
        for (AbstractTermTuple tuple : tuples) {
            if((postingList = search(tuple.term)) == null) {
                postingList = new PostingList();
                this.termToPostingListMapping.put(tuple.term, postingList);
                postingList.add(new Posting(document.getDocId(), 1, new ArrayList<>(List.of(tuple.curPos))));
            }
            else {
                AbstractPosting posting = postingList.get(postingList.indexOf(document.getDocId()));
                if (posting == null) {
                    postingList.add(new Posting(document.getDocId(), 1, new ArrayList<>(List.of(tuple.curPos))));
                } else {
                    posting.setFreq(posting.getFreq() + 1);
                    posting.getPositions().add(tuple.curPos);
                }
            }
        }
    }

    /**
     * <pre>
     * 从索引文件里加载已经构建好的索引.内部调用FileSerializable接口方法readObject即可
     * @param file ：索引文件
     * </pre>
     */
    @Override
    public void load(File file) {
        if (!file.exists()) {
            System.out.println("索引文件不存在");
        }
        else if (!file.isFile()) {
            System.out.println("索引文件不是一个文件");
        }
        else if (!file.canRead()) {
            System.out.println("索引文件不可读");
        }
        else {
            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
                readObject(in);
            } catch (IOException e) {
                System.out.println("读取索引文件失败");
                e.printStackTrace();
            }
        }
    }

    /**
     * <pre>
     * 将在内存里构建好的索引写入到文件. 内部调用FileSerializable接口方法writeObject即可
     * @param file ：写入的目标索引文件
     * </pre>
     */
    @Override
    public void save(File file) {
        if (!file.exists()) {
            System.out.println("索引文件不存在");
        }
        else if (!file.isFile()) {
            System.out.println("索引文件不是一个文件");
        }
        else if (!file.canRead()) {
            System.out.println("索引文件不可读");
        }
        else {
            try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file))) {
                writeObject(out);
            } catch (IOException e) {
                System.out.println("写入索引文件失败");
                e.printStackTrace();
            }
        }
    }

    /**
     * 返回指定单词的PostingList
     *
     * @param term : 指定的单词
     * @return ：指定单词的PostingList;如果索引字典没有该单词，则返回null
     */
    @Override
    public AbstractPostingList search(AbstractTerm term) {
        if (term == null) {
            System.out.println("term不能为空");
            return null;
        }
        return termToPostingListMapping.get(term);
    }

    /**
     * 返回索引的字典.字典为索引里所有单词的并集
     *
     * @return ：索引中Term列表
     */
    @Override
    public Set<AbstractTerm> getDictionary() {
        return termToPostingListMapping.keySet();
    }

    /**
     * <pre>
     * 对索引进行优化，包括：
     *      对索引里每个单词的PostingList按docId从小到大排序
     *      同时对每个Posting里的positions从小到大排序
     * 在内存中把索引构建完后执行该方法
     * </pre>
     */
    @Override
    public void optimize() {
        for (AbstractPostingList postingList : termToPostingListMapping.values()) {
            postingList.sort();
            for (int i = 0; i < postingList.size(); i++) {
                postingList.get(i).sort();
            }
        }
    }

    /**
     * 根据docId获得对应文档的完全路径名
     *
     * @param docId ：文档id
     * @return : 对应文档的完全路径名
     */
    @Override
    public String getDocName(int docId) {
        if (docIdToDocPathMapping.containsKey(docId)) {
            return docIdToDocPathMapping.get(docId);
        } else {
            System.out.println("索引字典没有该文档id");
            return null;
        }
    }

    /**
     * 写到二进制文件
     *
     * @param out :输出流对象
     */
    @Override
    public void writeObject(ObjectOutputStream out) {
        try {
            out.writeObject(docIdToDocPathMapping);
            out.writeObject(termToPostingListMapping);
        } catch (IOException e) {
            System.out.println("写入索引文件失败");
            e.printStackTrace();
        }
    }

    /**
     * 从二进制文件读
     *
     * @param in ：输入流对象
     */
    @Override
    public void readObject(ObjectInputStream in) {
        try {
            Object obj = in.readObject();
            if (obj instanceof HashMap) {
                docIdToDocPathMapping = (HashMap<Integer, String>) obj;
            } else {
                System.out.println("读取索引文件失败");
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("读取索引文件失败");
            e.printStackTrace();
        }
        try {
            Object obj = in.readObject();
            if (obj instanceof HashMap) {
                termToPostingListMapping = (HashMap<AbstractTerm, AbstractPostingList>) obj;
            } else {
                System.out.println("读取索引文件失败");
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("读取索引文件失败");
            e.printStackTrace();
        }
    }
}
