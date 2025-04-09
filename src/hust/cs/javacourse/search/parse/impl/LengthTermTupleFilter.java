package hust.cs.javacourse.search.parse.impl;

import hust.cs.javacourse.search.index.AbstractTermTuple;
import hust.cs.javacourse.search.parse.AbstractTermTupleFilter;
import hust.cs.javacourse.search.parse.AbstractTermTupleStream;
import hust.cs.javacourse.search.util.Config;

public class LengthTermTupleFilter extends AbstractTermTupleFilter {
    private int minLength = 3;
    private int maxLength = 20;
    /**
     * <pre>
     *     构造函数
     * </pre>
     *
     * @param input ：过滤器的输入，类型为AbstractTermTupleStream
     */
    public LengthTermTupleFilter(AbstractTermTupleStream input, int minLength, int maxLength) {
        super(input);
        this.minLength = minLength;
        this.maxLength = maxLength;
    }

    public LengthTermTupleFilter(AbstractTermTupleStream input) {
        super(input);
        this.minLength = Config.TERM_FILTER_MINLENGTH;
        this.maxLength = Config.TERM_FILTER_MAXLENGTH;
    }

    /**
     * <pre>
     *     实现父类AbstractTermTupleStream的next方法，返回下一个三元组
     * </pre>
     *
     * @return ：下一个三元组
     */
    @Override
    public AbstractTermTuple next() {
        AbstractTermTuple termTuple;
        while (true) {
            termTuple = input.next();
            if (termTuple == null) {
                return null;
            }
            // 如果长度小于等于2，继续读取下一个三元组
            if (termTuple.term.getContent().length() < minLength || termTuple.term.getContent().length() > maxLength) {
                continue;
            }
            break;
        }
        return termTuple;
    }
}
