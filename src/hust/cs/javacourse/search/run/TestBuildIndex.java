package hust.cs.javacourse.search.run;

import hust.cs.javacourse.search.index.AbstractDocumentBuilder;
import hust.cs.javacourse.search.index.AbstractIndex;
import hust.cs.javacourse.search.index.AbstractIndexBuilder;
import hust.cs.javacourse.search.index.impl.DocumentBuilder;
import hust.cs.javacourse.search.index.impl.IndexBuilder;
import hust.cs.javacourse.search.util.Config;

import java.io.File;

/**
 * 测试索引构建
 */
public class TestBuildIndex {
    /**
     *  索引构建程序入口
     * @param args : 命令行参数
     */
    public static void main(String[] args){
        // 1. 创建索引构建器
        AbstractIndexBuilder indexBuilder = new IndexBuilder(new DocumentBuilder());

        // 2. 构建索引
        String rootDirectory = "D:\\ZhangLin\\Desktop\\test_dat";
        AbstractIndex index = indexBuilder.buildIndex(rootDirectory);

        // 3. 打印索引信息
        System.out.println(index.toString());

    }
}
