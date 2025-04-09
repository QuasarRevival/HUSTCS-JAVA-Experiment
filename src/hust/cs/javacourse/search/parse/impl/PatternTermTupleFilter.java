package hust.cs.javacourse.search.parse.impl;

import hust.cs.javacourse.search.parse.AbstractTermTupleFilter;
import hust.cs.javacourse.search.parse.AbstractTermTupleStream;
import hust.cs.javacourse.search.index.AbstractTermTuple;
import hust.cs.javacourse.search.util.Config;

public class PatternTermTupleFilter extends AbstractTermTupleFilter {
    /**
     * <pre>
     *     正则表达式
     * </pre>
     */
    private String pattern;

    /**
     * <pre>
     *     构造函数
     * </pre>
     *
     * @param input ：过滤器的输入，类型为AbstractTermTupleStream
     */
    public PatternTermTupleFilter(AbstractTermTupleStream input) {
        super(input);
        this.pattern = Config.TERM_FILTER_PATTERN;
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
            // 如果不匹配正则表达式，继续读取下一个三元组
            if (!termTuple.term.getContent().matches(pattern)) {
                continue;
            }
            break;
        }
        return termTuple;
    }
}
