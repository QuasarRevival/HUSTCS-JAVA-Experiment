package hust.cs.javacourse.search.index.impl;

import hust.cs.javacourse.search.index.AbstractDocument;
import hust.cs.javacourse.search.index.AbstractDocumentBuilder;
import hust.cs.javacourse.search.index.AbstractIndexBuilder;
import hust.cs.javacourse.search.index.AbstractIndex;

import java.io.File;

public class IndexBuilder extends AbstractIndexBuilder {
    /**
     * <pre>
     * 构造函数
     * @param docBuilder：指定文档解析器
     * </pre>
     */
    public IndexBuilder(AbstractDocumentBuilder docBuilder) {
        super(docBuilder);
    }

    /**
     * <pre>
     * 构建指定目录下的所有文本文件的倒排索引.
     *      需要遍历和解析目录下的每个文本文件, 得到对应的Document对象，再依次加入到索引，并将索引保存到文件.
     * @param rootDirectory ：指定目录
     * @return ：构建好的索引
     * </pre>
     */
    @Override
    public AbstractIndex buildIndex(String rootDirectory) {
        AbstractIndex index = new Index();
        File rootDir = new File(rootDirectory);
        if (!rootDir.exists() || !rootDir.isDirectory()) {
            throw new IllegalArgumentException("指定的目录不存在或不是一个目录");
        }
        File[] files = rootDir.listFiles();
        if (files == null || files.length == 0) {
            throw new IllegalArgumentException("指定的目录下没有文件");
        }
        for (File file : files) {
            if (file.isFile()) {
                // 解析文件，构建Document对象
                AbstractDocument doc = docBuilder.build(docId++, file.getAbsolutePath(), file);
                // 将Document对象加入索引
                index.addDocument(doc);
            }
        }
        // 将索引保存到文件
        File indexFile = new File(rootDirectory + File.separator + "index.dat");
        try {
            index.save(indexFile);
        } catch (Exception e) {
            throw new RuntimeException("索引保存失败", e);
        }
        return index;
    }
}
