package hust.cs.javacourse.search.parse.impl;

import hust.cs.javacourse.search.index.AbstractTermTuple;
import hust.cs.javacourse.search.index.impl.Term;
import hust.cs.javacourse.search.index.impl.TermTuple;
import hust.cs.javacourse.search.parse.AbstractTermTupleScanner;
import hust.cs.javacourse.search.util.Config;
import hust.cs.javacourse.search.util.StringSplitter;

import java.io.IOException;

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
        String read;
        StringSplitter splitter = new StringSplitter();
        splitter.setSplitRegex(Config.STRING_SPLITTER_REGEX);
        while (true) {
            try {
                if ((read = input.readLine()) == null) break;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            termList.addAll(splitter.splitByRegex(read));
        }
    }

    /**
     * 获得下一个三元组
     * @return: 下一个三元组；如果到了流的末尾，返回null
     */
    @Override
    public AbstractTermTuple next(){
        if (curIndex >= termList.size()) {
            return null;
        }
        String termStr = termList.get(curIndex);
        curIndex++;
        return new TermTuple(new Term(termStr) , curIndex);
    }
}
