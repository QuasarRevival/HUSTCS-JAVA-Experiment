package hust.cs.javacourse.search.parse.impl;

import hust.cs.javacourse.search.index.AbstractTermTuple;
import hust.cs.javacourse.search.parse.AbstractTermTupleFilter;
import hust.cs.javacourse.search.parse.AbstractTermTupleStream;
import hust.cs.javacourse.search.util.StopWords;

public class StopWordTermTupleFilter extends AbstractTermTupleFilter {
    /**
     * <pre>
     * 构造函数
     * @param input: Filter的输入，类型为AbstractTermTupleStream
     * </pre>
     */
    public StopWordTermTupleFilter(AbstractTermTupleStream input) {
        super(input);
    }

    /**
     * 实现父类AbstractTermTupleStream的next方法，返回下一个三元组
     */
    @Override
    public AbstractTermTuple next() {
        AbstractTermTuple termTuple;
        while (true) {
            termTuple = input.next();
            if (termTuple == null) {
                return null;
            }
            // 如果是停用词，继续读取下一个三元组
            if (StopWords.STOP_WORDS.contains(termTuple.term.getContent())) {
                continue;
            }
            break;
        }
        return termTuple;
    }
}
