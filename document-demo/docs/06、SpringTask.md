## SpringTask

由于SpringTask已经存在于Spring框架中，所以无需添加依赖。




添加配置： src/main/java/document/run/config/SpringTaskConfig.java

Cron表达式
Cron表达式是一个字符串，包括6~7个时间元素，在SpringTask中可以用于指定任务的执行时间。


时间元素 |	可出现的字符	| 有效数值范围
:- | :- | :-
Seconds|	, - * /	|0-59
Minutes|	, - * /	|0-59
Hours|	, - * /	0|-23
DayofMonth|	, - * / ? L W|	0-31
Month	|, - * /	|1-12
DayofWeek|	, - * / ? L #	|1-7或SUN-SAT
