
----4
Select c.client_id, c.surname, ca.CLIENT_ACCOUNT_NUMBER, ca.DISPLAY_BALANCE,ac.description
from  CLIENT  c, CLIENT_ACCOUNT ca, ACCOUNT_TYPE ac
inner join
( select  c.client_id ,max(ca.DISPLAY_BALANCE) as highest
		from  CLIENT_ACCOUNT ca, client c
                where c.client_id = ca.client_id
                group by c.client_id
 )
where ca.DISPLAY_BALANCE  = highest
and c.CLIENT_ID = ca.CLIENT_ID
and ac.transactional = true
and ca.ACCOUNT_TYPE_CODE = ac.ACCOUNT_TYPE_CODE;

-------5
Select c.TITLE, c.NAME, c.SURNAME, Table1.LOAN_AMOUNT,Table2.TRANSACTIONAL, (IFNULL(Table1.LOAN_AMOUNT,0) + IFNULL(Table2.TRANSACTIONAL ,0)) AS Net_Position from CLIENT c

LEFT JOIN (Select C.client_id As CLIENT_ID ,sum( ca.DISPLAY_BALANCE) AS LOAN_AMOUNT

FROM CLIENT cpso
join CLIENT_ACCOUNT ca on  c.CLIENT_ID = ca.CLIENT_ID
join ACCOUNT_TYPE AT on ca.ACCOUNT_TYPE_CODE = AT.ACCOUNT_TYPE_CODE AND AT.ACCOUNT_TYPE_CODE  IN ('PLOAN', 'HLOAN')
Group by c.CLIENT_id) as Table1
ON c.CLIENT_ID = Table1.CLIENT_ID

LEFT JOIN
(SELECT  ca.CLIENT_ID , SUM (ca.display_balance) as TRANSACTIONAL  FROM  CLIENT_ACCOUNT ca
join ACCOUNT_TYPE AT on ca.ACCOUNT_TYPE_CODE = AT.ACCOUNT_TYPE_CODE WHERE AT.transactional = 'TRUE'
GROUP BY ca.CLIENT_ID ) as Table2
ON c.CLIENT_ID = Table2.CLIENT_ID;