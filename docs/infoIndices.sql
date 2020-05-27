SELECT  index_owner, index_name, table_name, column_name
FROM all_ind_columns  atc 
where index_owner='ISIS2304B192010'
and atc.index_name not like 'B%';
