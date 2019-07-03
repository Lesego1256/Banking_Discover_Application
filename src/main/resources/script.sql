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


