package hust.cs.javacourse.search.query.impl;

import hust.cs.javacourse.search.index.AbstractPostingList;
import hust.cs.javacourse.search.index.AbstractTerm;
import hust.cs.javacourse.search.query.AbstractHit;
import hust.cs.javacourse.search.query.AbstractIndexSearcher;
import hust.cs.javacourse.search.query.Sort;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class IndexSearcher extends AbstractIndexSearcher {
    /**
     * <pre>
     *     缺省构造函数
     * </pre>
     */
    public IndexSearcher() {
        super();
    }
    /**
     * <pre>
     *     根据索引文件的绝对路径打开索引
     * </pre>
     *
     * @param indexFile ：索引文件的绝对路径
     */
    @Override
    public void open(String indexFile) {
        this.index.load(new File(indexFile));
    }

    /**
     * 根据单个检索词进行搜索
     * @param queryTerm ：检索词
     * @param sorter ：排序器
     * @return ：命中结果数组
     */
    @Override
    public AbstractHit[] search(AbstractTerm queryTerm, Sort sorter) {
        AbstractPostingList postingList = this.index.search(queryTerm);
        List<AbstractHit> hits = new ArrayList<>();
        if (postingList == null) {
            return new AbstractHit[0];
        }
        for (int i = 0; i < postingList.size(); i++) {
            AbstractHit hit = new Hit(postingList.get(i).getDocId(), index.getDocName(postingList.get(i).getDocId()));
            hit.getTermPostingMapping()
                    .put(queryTerm, postingList.get(i));
            hit.setScore(sorter.score(hit));
            hits.add(hit);
        }
        sorter.sort(hits);
        return hits.toArray(new AbstractHit[hits.size()]);
    }

    /**
     * 根据二个检索词进行搜索
     * @param queryTerm1 ：第1个检索词
     * @param queryTerm2 ：第2个检索词
     * @param sorter ：    排序器
     * @param combine ：   多个检索词的逻辑组合方式
     * @return ：命中结果数组
     */
    @Override
    public AbstractHit[] search(AbstractTerm queryTerm1, AbstractTerm queryTerm2, Sort sorter, LogicalCombination combine) {
        List<AbstractHit> hits1 = new ArrayList<>(List.of(search(queryTerm1, sorter)));
        List<AbstractHit> hits2 = new ArrayList<>(List.of(search(queryTerm2, sorter)));
        List<AbstractHit> and = new ArrayList<>();
        for (AbstractHit hit1 : hits1) {
            for (AbstractHit hit2 : hits2) {
                if (hit1.getDocId() == hit2.getDocId()) {
                    hit1.getTermPostingMapping()
                            .putAll(hit2.getTermPostingMapping());
                    hit1.setScore(hit1.getScore() + hit2.getScore());
                    and.add(hit1);
                    hits1.remove(hit1);
                    hits2.remove(hit2);
                }
            }
        }
        if (combine == LogicalCombination.AND) {
            sorter.sort(and);
            return and.toArray(new AbstractHit[and.size()]);
        } else {
            and.addAll(hits1);
            and.addAll(hits2);
            sorter.sort(and);
            return and.toArray(new AbstractHit[and.size()]);
        }
    }
}
