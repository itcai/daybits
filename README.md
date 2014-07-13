daybits
=======

压缩表示日期的一种数据格式。阿里巴巴集团安全部数据团队在清洗大数据的过程中，经常需要保存精度到天的时间数据，比如说某人在历史哪些天登录过。这样的数据很多，需要关注存储成本。daybits是为解决这个问题而设计的压缩表示日期数据的数据格式。

数据格式
=======

      daybits := <beforeYears>#<years>
      beforeYears := <year>;<year>; ...
      years := <year>;<year>;<year>; ...
      year := <quarter>,<quarter>,<quarter>,<quarter>
      quarter = base64_str
      
其中#之前是2013以前的年，如果#不存在，或者#之后的是2013年开始的数据

odps函数
=======
<table>
	<tr>
		<td>函数名</td>
		<td>返回数据类型</td>
		<td>函数类型</td>
		<td>UDF实现类</td>
		<td>函数介绍</td>
	</tr>
	<tr>
		<td>daybits_concat</td>
		<td>String</td>
		<td>UDAF</td>
		<td>com.alibaba.daybits.support.odps.udf.DayBitsConcat</td>
		<td>聚合函数，输入参数是日期，返回daybits格式字符串，用于在原始明细数据中构建daybits数据</td>
	</tr>
	<tr>
		<td>daybits_merge</td>
		<td>String</td>
		<td>UDAF</td>
		<td>com.alibaba.daybits.support.odps.udf.DayBitsMerge</td>
		<td>聚合函数，输入参数是daybits数据，返回是合并之后的daybits数据</td>
	</tr>
	<tr>
		<td>daybits_count</td>
		<td>Bigint</td>
		<td>UDF</td>
		<td>com.alibaba.daybits.support.odps.udf.DayBitsCount</td>
		<td>查看daybits数据中存在多少天，输入是daybits数据，可以指定开始和结束时间</td>
	</tr>
	<tr>
		<td>daybits_explain</td>
		<td>String</td>
		<td>UDF</td>
		<td>com.alibaba.daybits.support.odps.udf.DayBitsExplain</td>
		<td>把daybits解析为可读的字符串，输入是daybits数据，可以指定开始和结束时间</td>
	</tr>
	<tr>
		<td>daybits_first</td>
		<td>BigInt</td>
		<td>UDF</td>
		<td>com.alibaba.daybits.support.odps.udf.DayBitsFirst</td>
		<td>返回第日期</td>
	</tr>	
	<tr>
		<td>daybits_last</td>
		<td>BigInt</td>
		<td>UDF</td>
		<td>com.alibaba.daybits.support.odps.udf.DayBitsLast</td>
		<td>返回最后日期</td>
	</tr>
	<tr>
		<td>daybits_and</td>
		<td>String</td>
		<td>UDF</td>
		<td>com.alibaba.daybits.support.odps.udf.DayBitsAnd</td>
		<td>daybits数据的交集</td>
	</tr>
	<tr>
		<td>daybits_or</td>
		<td>String</td>
		<td>UDF</td>
		<td>com.alibaba.daybits.support.odps.udf.DayBitsOr</td>
		<td>dyabits数据的并集</td>
	</tr>
	<tr>
		<td>daybits_get</td>
		<td>Boolean</td>
		<td>UDF</td><td>com.alibaba.daybits.support.odps.udf.DayBitsGet</td>
		<td>判断某个日期在daybits中是否存在</td>
	</tr>
	<tr>
		<td>daybits_set</td>
		<td>String</td>
		<td>UDF</td>
		<td>com.alibaba.daybits.support.odps.udf.DayBitsSet</td>
		<td>设置daybits某一天为true</td>
	</tr>
</table>
