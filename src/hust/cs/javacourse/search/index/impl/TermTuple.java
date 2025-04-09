package hust.cs.javacourse.search.index.impl;

import hust.cs.javacourse.search.index.AbstractTermTuple;
import hust.cs.javacourse.search.index.AbstractTerm;

public class TermTuple extends AbstractTermTuple {
    /**
     * <pre>
     * 默认构造函数
     * </pre>
     */
    public TermTuple() {
        super();
    }
    /**
     * <pre>
     * 构造函数
     * @param term: 单词
     * @param curPos: 单词出现的当前位置
     * </pre>
     */
    public TermTuple(AbstractTerm term, int curPos) {
        super(term, curPos);
    }

    /**
     * 判断二个三元组内容是否相同
     * @param obj ：要比较的另外一个三元组
     * @return 如果内容相等（三个属性内容都相等）返回true，否则返回false
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        TermTuple termTuple = (TermTuple) obj;
        return curPos == termTuple.curPos && term.equals(termTuple.term);
    }

    /**
     * 获得三元组的字符串表示
     * @return ： 三元组的字符串表示
     */
    @Override
    public String toString() {
        return "TermTuple{" +
                "term=" + term +
                ", freq=" + freq +
                ", curPos=" + curPos +
                '}';
    }
}
