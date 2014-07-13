daybits
=======

压缩表示日期的一种数据格式。阿里巴巴集团安全部数据团队在清洗大数据的过程中，经常需要保存精度到天的时间数据，比如说某人在历史哪些天登录过。这样的数据很多，需要关注存储成本。daybits是为解决这个问题而设计的压缩表示日期数据的数据格式。


odps函数
=======
<table>
	<tr><td>函数名</td><td>函数类型</td><td>UDF实现类</td><td>函数介绍</td></tr>
	<tr><td>daybits_and</td><td>UDF</td><td>com.alibaba.daybits.support.odps.udf.DayBitsAnd</td><td></td></tr>
	<tr><td>daybits_concat</td><td>UDAF</td><td>com.alibaba.daybits.support.odps.udf.DayBitsConcat</td><td></td></tr>
	<tr><td>daybits_count</td><td>UDF</td><td>com.alibaba.daybits.support.odps.udf.DayBitsCount</td><td></td></tr>
	<tr><td>daybits_explain</td><td>UDF</td><td>com.alibaba.daybits.support.odps.udf.DayBitsExplain</td><td></td></tr>
	<tr><td>daybits_first</td><td>UDF</td><td>com.alibaba.daybits.support.odps.udf.DayBitsFirst</td><td></td></tr>
	<tr><td>daybits_get</td><td>UDF</td><td>com.alibaba.daybits.support.odps.udf.DayBitsGet</td><td></td></tr>
	<tr><td>daybits_last</td><td>UDF</td><td>com.alibaba.daybits.support.odps.udf.DayBitsLast</td><td></td></tr>
	<tr><td>daybits_merge</td><td>UDAF</td><td>com.alibaba.daybits.support.odps.udf.DayBitsMerge</td><td></td></tr>
	<tr><td>daybits_or</td><td>UDF</td><td>com.alibaba.daybits.support.odps.udf.DayBitsOr</td><td></td></tr>
	<tr><td>daybits_set</td><td>UDF</td><td>com.alibaba.daybits.support.odps.udf.DayBitsSet</td><td></td></tr>
</table>
