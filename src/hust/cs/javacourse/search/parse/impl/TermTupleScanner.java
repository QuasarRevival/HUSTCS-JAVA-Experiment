package hust.cs.javacourse.search.parse.impl;

import hust.cs.javacourse.search.index.AbstractTermTuple;
import hust.cs.javacourse.search.parse.AbstractTermTupleScanner;

public class TermTupleScanner extends AbstractTermTupleScanner {
    /**
     * <pre>
     * 缺省构造函数
     * </pre>
     */
    public TermTupleScanner() {
        super();
    }

    /**
     * <pre>
     * 构造函数
     * @param input：指定输入流对象，应该关联到一个文本文件
     * </pre>
     */
    public TermTupleScanner(java.io.BufferedReader input) {
        super(input);
    }

    /**
     * 获得下一个三元组
     * @return: 下一个三元组；如果到了流的末尾，返回null
     */
    @Override
    public AbstractTermTuple next(){

    }
}
