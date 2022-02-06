create table if not exists directory (
	id serial primary key,
	name varchar (50) not null,
	parent_id int,
	foreign key (parent_id) references directory
);

create or replace view path_mapping as (
	with recursive directory_hierarchy as (
		select 
			d1.id, 
			d1.name, 
			d1.parent_id,
			d1.name::VARCHAR as dir_path
		from directory as d1
		where d1.parent_id is null
		
		union all
		
		select 
			d2.id, 
			d2.name, 
			d2.parent_id,
			dir_path::VARCHAR || '\' || d2.name::VARCHAR
		from directory as d2
		join directory_hierarchy dh on d2.parent_id = dh.id  
	) select id as dir_id, name as dir_name, dir_path from directory_hierarchy
);
