delete from managers where manager_id >= 1;
insert into managers values (1, 'leo', 'root', 'ADMIN', null);

delete from developers where developer_id >= 1;
insert into developers (active,experience,name,name_of_current_project,number_of_bugfixing_tasks,number_of_development_tasks,number_of_refactoring_tasks,number_of_tasks,password,qualification,rnk,role,state)
values(0, 0, 'BAL CAPONE',null, 0,0,0,0, 'dev', 10, 'JUNIOR', 'USER', 'NEW_COMER' );

insert into developers (active,experience,name,name_of_current_project,number_of_bugfixing_tasks,number_of_development_tasks,number_of_refactoring_tasks,number_of_tasks,password,qualification,rnk,role,state)
values(0, 0, 'CAL CAPONE',null, 0,0,0,0, 'dev', 10, 'JUNIOR', 'USER', 'NEW_COMER' );

insert into developers (active,experience,name,name_of_current_project,number_of_bugfixing_tasks,number_of_development_tasks,number_of_refactoring_tasks,number_of_tasks,password,qualification,rnk,role,state)
values(0, 0, 'DAL CAPONE',null, 0,0,0,0, 'dev', 33, 'JUNIOR', 'USER', 'NEW_COMER' );

insert into developers (active,experience,name,name_of_current_project,number_of_bugfixing_tasks,number_of_development_tasks,number_of_refactoring_tasks,number_of_tasks,password,qualification,rnk,role,state)
values(0, 1, 'EAL CAPONE',null, 0,0,0,0, 'dev', 44, 'JUNIOR', 'USER', 'NEW_COMER' );

insert into developers (active,experience,name,name_of_current_project,number_of_bugfixing_tasks,number_of_development_tasks,number_of_refactoring_tasks,number_of_tasks,password,qualification,rnk,role,state)
values(0, 1, 'HAL CAPONE',null, 0,0,0,0, 'dev', 55, 'JUNIOR', 'USER', 'NEW_COMER' );

insert into developers (active,experience,name,name_of_current_project,number_of_bugfixing_tasks,number_of_development_tasks,number_of_refactoring_tasks,number_of_tasks,password,qualification,rnk,role,state)
values(0, 2, 'IAL CAPONE',null, 0,0,0,0, 'dev', 78, 'MIDDLE', 'USER', 'NEW_COMER' );

insert into developers (active,experience,name,name_of_current_project,number_of_bugfixing_tasks,number_of_development_tasks,number_of_refactoring_tasks,number_of_tasks,password,qualification,rnk,role,state)
values(0, 3, 'JAL CAPONE',null, 0,0,0,0, 'dev', 77, 'MIDDLE', 'USER', 'NEW_COMER' );

insert into developers (active,experience,name,name_of_current_project,number_of_bugfixing_tasks,number_of_development_tasks,number_of_refactoring_tasks,number_of_tasks,password,qualification,rnk,role,state)
values(0, 3, 'KAL CAPONE',null, 0,0,0,0, 'dev', 66, 'MIDDLE', 'USER', 'NEW_COMER' );

insert into developers (active,experience,name,name_of_current_project,number_of_bugfixing_tasks,number_of_development_tasks,number_of_refactoring_tasks,number_of_tasks,password,qualification,rnk,role,state)
values(0, 4, 'LAL CAPONE',null, 0,0,0,0, 'dev', 88, 'MIDDLE', 'USER', 'NEW_COMER' );

insert into developers (active,experience,name,name_of_current_project,number_of_bugfixing_tasks,number_of_development_tasks,number_of_refactoring_tasks,number_of_tasks,password,qualification,rnk,role,state)
values(0, 2, 'MAL CAPONE',null, 0,0,0,0, 'dev', 87, 'MIDDLE', 'USER', 'NEW_COMER' );

insert into developers (active,experience,name,name_of_current_project,number_of_bugfixing_tasks,number_of_development_tasks,number_of_refactoring_tasks,number_of_tasks,password,qualification,rnk,role,state)
values(0, 2, 'NAL CAPONE',null, 0,0,0,0, 'dev', 67, 'MIDDLE', 'USER', 'NEW_COMER' );

insert into developers (active,experience,name,name_of_current_project,number_of_bugfixing_tasks,number_of_development_tasks,number_of_refactoring_tasks,number_of_tasks,password,qualification,rnk,role,state)
values(0, 2, 'OAL CAPONE',null, 0,0,0,0, 'dev', 87, 'MIDDLE', 'USER', 'NEW_COMER' );

insert into developers (active,experience,name,name_of_current_project,number_of_bugfixing_tasks,number_of_development_tasks,number_of_refactoring_tasks,number_of_tasks,password,qualification,rnk,role,state)
values(0, 2, 'PAL CAPONE',null, 0,0,0,0, 'dev', 77, 'MIDDLE', 'USER', 'NEW_COMER' );

insert into developers (active,experience,name,name_of_current_project,number_of_bugfixing_tasks,number_of_development_tasks,number_of_refactoring_tasks,number_of_tasks,password,qualification,rnk,role,state)
values(0, 2, 'QAL CAPONE',null, 0,0,0,0, 'dev', 80, 'MIDDLE', 'USER', 'NEW_COMER' );

insert into developers (active,experience,name,name_of_current_project,number_of_bugfixing_tasks,number_of_development_tasks,number_of_refactoring_tasks,number_of_tasks,password,qualification,rnk,role,state)
values(0, 6, 'RAL CAPONE',null, 0,0,0,0, 'dev', 90, 'SENIOR', 'USER', 'NEW_COMER' );

insert into developers (active,experience,name,name_of_current_project,number_of_bugfixing_tasks,number_of_development_tasks,number_of_refactoring_tasks,number_of_tasks,password,qualification,rnk,role,state)
values(0, 7, 'SAL CAPONE',null, 0,0,0,0, 'dev', 100, 'SENIOR', 'USER', 'NEW_COMER' );

delete from tasks where id > 0;
delete from projects where id > 0;

insert into projects (juniors_need,juniors_on_project,max_tasks_for_one_dev,middles_need,middles_on_project,name,seniors_need,seniors_on_project)
values(2,0,5,3,0,'ALPHA',2,0);

insert into projects (juniors_need,juniors_on_project,max_tasks_for_one_dev,middles_need,middles_on_project,name,seniors_need,seniors_on_project)
values(5,0,5,5,0,'BETA',1,0);

#ALPHA

insert into tasks (description,end_date,estimation_status,execution_status,executorid,project_name,start_date,status,story_points,task_type)
values ('..add description..', DATE_ADD(curdate(),INTERVAL 1 DAY), 'NOT_ESTIMATED', 'NOT_DONE', 0, 'ALPHA',curdate(),'NOT_ASSIGNED', 1, 'BUGFIXING');

insert into tasks (description,end_date,estimation_status,execution_status,executorid,project_name,start_date,status,story_points,task_type)
values ('..add description..', DATE_ADD(curdate(),INTERVAL 1 DAY), 'NOT_ESTIMATED', 'NOT_DONE', 0, 'ALPHA',curdate(),'NOT_ASSIGNED', 1, 'BUGFIXING');

insert into tasks (description,end_date,estimation_status,execution_status,executorid,project_name,start_date,status,story_points,task_type)
values ('..add description..', DATE_ADD(curdate(),INTERVAL 1 DAY), 'NOT_ESTIMATED', 'NOT_DONE', 0, 'ALPHA',curdate(),'NOT_ASSIGNED', 1, 'BUGFIXING');

insert into tasks (description,end_date,estimation_status,execution_status,executorid,project_name,start_date,status,story_points,task_type)
values ('..add description..', DATE_ADD(curdate(),INTERVAL 1 DAY), 'NOT_ESTIMATED', 'NOT_DONE', 0, 'ALPHA',curdate(),'NOT_ASSIGNED', 2, 'BUGFIXING');

insert into tasks (description,end_date,estimation_status,execution_status,executorid,project_name,start_date,status,story_points,task_type)
values ('..add description..', DATE_ADD(curdate(),INTERVAL 1 DAY), 'NOT_ESTIMATED', 'NOT_DONE', 0, 'ALPHA',curdate(),'NOT_ASSIGNED', 2, 'BUGFIXING');

insert into tasks (description,end_date,estimation_status,execution_status,executorid,project_name,start_date,status,story_points,task_type)
values ('..add description..', DATE_ADD(curdate(),INTERVAL 1 DAY), 'NOT_ESTIMATED', 'NOT_DONE', 0, 'ALPHA',curdate(),'NOT_ASSIGNED', 2, 'BUGFIXING');

insert into tasks (description,end_date,estimation_status,execution_status,executorid,project_name,start_date,status,story_points,task_type)
values ('..add description..', DATE_ADD(curdate(),INTERVAL 1 DAY), 'NOT_ESTIMATED', 'NOT_DONE', 0, 'ALPHA',curdate(),'NOT_ASSIGNED', 1, 'BUGFIXING');

insert into tasks (description,end_date,estimation_status,execution_status,executorid,project_name,start_date,status,story_points,task_type)
values ('..add description..', DATE_ADD(curdate(),INTERVAL 1 DAY), 'NOT_ESTIMATED', 'NOT_DONE', 0, 'ALPHA',curdate(),'NOT_ASSIGNED', 1, 'BUGFIXING');

insert into tasks (description,end_date,estimation_status,execution_status,executorid,project_name,start_date,status,story_points,task_type)
values ('..add description..', DATE_ADD(curdate(),INTERVAL 1 DAY), 'NOT_ESTIMATED', 'NOT_DONE', 0, 'ALPHA',curdate(),'NOT_ASSIGNED', 1, 'BUGFIXING');

insert into tasks (description,end_date,estimation_status,execution_status,executorid,project_name,start_date,status,story_points,task_type)
values ('..add description..', DATE_ADD(curdate(),INTERVAL 1 DAY), 'NOT_ESTIMATED', 'NOT_DONE', 0, 'ALPHA',curdate(),'NOT_ASSIGNED', 2, 'BUGFIXING');

insert into tasks (description,end_date,estimation_status,execution_status,executorid,project_name,start_date,status,story_points,task_type)
values ('..add description..', DATE_ADD(curdate(),INTERVAL 1 DAY), 'NOT_ESTIMATED', 'NOT_DONE', 0, 'ALPHA',curdate(),'NOT_ASSIGNED', 3, 'DEVELOPMENT');

insert into tasks (description,end_date,estimation_status,execution_status,executorid,project_name,start_date,status,story_points,task_type)
values ('..add description..', DATE_ADD(curdate(),INTERVAL 1 DAY), 'NOT_ESTIMATED', 'NOT_DONE', 0, 'ALPHA',curdate(),'NOT_ASSIGNED', 2, 'DEVELOPMENT');

insert into tasks (description,end_date,estimation_status,execution_status,executorid,project_name,start_date,status,story_points,task_type)
values ('..add description..', DATE_ADD(curdate(),INTERVAL 4 DAY), 'NOT_ESTIMATED', 'NOT_DONE', 0, 'ALPHA',curdate(),'NOT_ASSIGNED', 1, 'DEVELOPMENT');

insert into tasks (description,end_date,estimation_status,execution_status,executorid,project_name,start_date,status,story_points,task_type)
values ('..add description..', DATE_ADD(curdate(),INTERVAL 8 DAY), 'NOT_ESTIMATED', 'NOT_DONE', 0, 'ALPHA',curdate(),'NOT_ASSIGNED', 1, 'DEVELOPMENT');

insert into tasks (description,end_date,estimation_status,execution_status,executorid,project_name,start_date,status,story_points,task_type)
values ('..add description..', DATE_ADD(curdate(),INTERVAL 7 DAY), 'NOT_ESTIMATED', 'NOT_DONE', 0, 'ALPHA',curdate(),'NOT_ASSIGNED', 2, 'DEVELOPMENT');

insert into tasks (description,end_date,estimation_status,execution_status,executorid,project_name,start_date,status,story_points,task_type)
values ('..add description..', DATE_ADD(curdate(),INTERVAL 8 DAY), 'NOT_ESTIMATED', 'NOT_DONE', 0, 'ALPHA',curdate(),'NOT_ASSIGNED', 3, 'DEVELOPMENT');

insert into tasks (description,end_date,estimation_status,execution_status,executorid,project_name,start_date,status,story_points,task_type)
values ('..add description..', DATE_ADD(curdate(),INTERVAL 11 DAY), 'NOT_ESTIMATED', 'NOT_DONE', 0, 'ALPHA',curdate(),'NOT_ASSIGNED', 1, 'DEVELOPMENT');

insert into tasks (description,end_date,estimation_status,execution_status,executorid,project_name,start_date,status,story_points,task_type)
values ('..add description..', DATE_ADD(curdate(),INTERVAL 12 DAY), 'NOT_ESTIMATED', 'NOT_DONE', 0, 'ALPHA',curdate(),'NOT_ASSIGNED', 1, 'DEVELOPMENT');

insert into tasks (description,end_date,estimation_status,execution_status,executorid,project_name,start_date,status,story_points,task_type)
values ('..add description..', DATE_ADD(curdate(),INTERVAL 10 DAY), 'NOT_ESTIMATED', 'NOT_DONE', 0, 'ALPHA',curdate(),'NOT_ASSIGNED', 2, 'DEVELOPMENT');

insert into tasks (description,end_date,estimation_status,execution_status,executorid,project_name,start_date,status,story_points,task_type)
values ('..add description..', DATE_ADD(curdate(),INTERVAL 3 DAY), 'NOT_ESTIMATED', 'NOT_DONE', 0, 'ALPHA',curdate(),'NOT_ASSIGNED', 3, 'DEVELOPMENT');

insert into tasks (description,end_date,estimation_status,execution_status,executorid,project_name,start_date,status,story_points,task_type)
values ('..add description..', DATE_ADD(curdate(),INTERVAL 3 DAY), 'NOT_ESTIMATED', 'NOT_DONE', 0, 'ALPHA',curdate(),'NOT_ASSIGNED', 4, 'REFACTORING');

insert into tasks (description,end_date,estimation_status,execution_status,executorid,project_name,start_date,status,story_points,task_type)
values ('..add description..', DATE_ADD(curdate(),INTERVAL 3 DAY), 'NOT_ESTIMATED', 'NOT_DONE', 0, 'ALPHA',curdate(),'NOT_ASSIGNED', 5, 'REFACTORING');

insert into tasks (description,end_date,estimation_status,execution_status,executorid,project_name,start_date,status,story_points,task_type)
values ('..add description..', DATE_ADD(curdate(),INTERVAL 5 DAY), 'NOT_ESTIMATED', 'NOT_DONE', 0, 'ALPHA',curdate(),'NOT_ASSIGNED', 2, 'REFACTORING');

insert into tasks (description,end_date,estimation_status,execution_status,executorid,project_name,start_date,status,story_points,task_type)
values ('..add description..', DATE_ADD(curdate(),INTERVAL 5 DAY), 'NOT_ESTIMATED', 'NOT_DONE', 0, 'ALPHA',curdate(),'NOT_ASSIGNED', 1, 'REFACTORING');

insert into tasks (description,end_date,estimation_status,execution_status,executorid,project_name,start_date,status,story_points,task_type)
values ('..add description..', DATE_ADD(curdate(),INTERVAL 5 DAY), 'NOT_ESTIMATED', 'NOT_DONE', 0, 'ALPHA',curdate(),'NOT_ASSIGNED', 3, 'REFACTORING');

insert into tasks (description,end_date,estimation_status,execution_status,executorid,project_name,start_date,status,story_points,task_type)
values ('..add description..', DATE_ADD(curdate(),INTERVAL 5 DAY), 'NOT_ESTIMATED', 'NOT_DONE', 0, 'ALPHA',curdate(),'NOT_ASSIGNED', 1, 'REFACTORING');

insert into tasks (description,end_date,estimation_status,execution_status,executorid,project_name,start_date,status,story_points,task_type)
values ('..add description..', DATE_ADD(curdate(),INTERVAL 5 DAY), 'NOT_ESTIMATED', 'NOT_DONE', 0, 'ALPHA',curdate(),'NOT_ASSIGNED', 4, 'REFACTORING');

insert into tasks (description,end_date,estimation_status,execution_status,executorid,project_name,start_date,status,story_points,task_type)
values ('..add description..', DATE_ADD(curdate(),INTERVAL 5 DAY), 'NOT_ESTIMATED', 'NOT_DONE', 0, 'ALPHA',curdate(),'NOT_ASSIGNED', 5, 'REFACTORING');

insert into tasks (description,end_date,estimation_status,execution_status,executorid,project_name,start_date,status,story_points,task_type)
values ('..add description..', DATE_ADD(curdate(),INTERVAL 5 DAY), 'NOT_ESTIMATED', 'NOT_DONE', 0, 'ALPHA',curdate(),'NOT_ASSIGNED', 6, 'REFACTORING');

insert into tasks (description,end_date,estimation_status,execution_status,executorid,project_name,start_date,status,story_points,task_type)
values ('..add description..', DATE_ADD(curdate(),INTERVAL 5 DAY), 'NOT_ESTIMATED', 'NOT_DONE', 0, 'ALPHA',curdate(),'NOT_ASSIGNED', 1, 'REFACTORING');

#BETA

insert into tasks (description,end_date,estimation_status,execution_status,executorid,project_name,start_date,status,story_points,task_type)
values ('..add description..', DATE_ADD(curdate(),INTERVAL 1 DAY), 'NOT_ESTIMATED', 'NOT_DONE', 0, 'BETA',curdate(),'NOT_ASSIGNED', 1, 'BUGFIXING');

insert into tasks (description,end_date,estimation_status,execution_status,executorid,project_name,start_date,status,story_points,task_type)
values ('..add description..', DATE_ADD(curdate(),INTERVAL 1 DAY), 'NOT_ESTIMATED', 'NOT_DONE', 0, 'BETA',curdate(),'NOT_ASSIGNED', 1, 'BUGFIXING');

insert into tasks (description,end_date,estimation_status,execution_status,executorid,project_name,start_date,status,story_points,task_type)
values ('..add description..', DATE_ADD(curdate(),INTERVAL 1 DAY), 'NOT_ESTIMATED', 'NOT_DONE', 0, 'BETA',curdate(),'NOT_ASSIGNED', 1, 'BUGFIXING');

insert into tasks (description,end_date,estimation_status,execution_status,executorid,project_name,start_date,status,story_points,task_type)
values ('..add description..', DATE_ADD(curdate(),INTERVAL 1 DAY), 'NOT_ESTIMATED', 'NOT_DONE', 0, 'BETA',curdate(),'NOT_ASSIGNED', 1, 'BUGFIXING');

insert into tasks (description,end_date,estimation_status,execution_status,executorid,project_name,start_date,status,story_points,task_type)
values ('..add description..', DATE_ADD(curdate(),INTERVAL 1 DAY), 'NOT_ESTIMATED', 'NOT_DONE', 0, 'BETA',curdate(),'NOT_ASSIGNED', 1, 'BUGFIXING');

insert into tasks (description,end_date,estimation_status,execution_status,executorid,project_name,start_date,status,story_points,task_type)
values ('..add description..', DATE_ADD(curdate(),INTERVAL 1 DAY), 'NOT_ESTIMATED', 'NOT_DONE', 0, 'BETA',curdate(),'NOT_ASSIGNED', 1, 'BUGFIXING');

insert into tasks (description,end_date,estimation_status,execution_status,executorid,project_name,start_date,status,story_points,task_type)
values ('..add description..', DATE_ADD(curdate(),INTERVAL 1 DAY), 'NOT_ESTIMATED', 'NOT_DONE', 0, 'BETA',curdate(),'NOT_ASSIGNED', 1, 'BUGFIXING');

insert into tasks (description,end_date,estimation_status,execution_status,executorid,project_name,start_date,status,story_points,task_type)
values ('..add description..', DATE_ADD(curdate(),INTERVAL 1 DAY), 'NOT_ESTIMATED', 'NOT_DONE', 0, 'BETA',curdate(),'NOT_ASSIGNED', 1, 'BUGFIXING');

insert into tasks (description,end_date,estimation_status,execution_status,executorid,project_name,start_date,status,story_points,task_type)
values ('..add description..', DATE_ADD(curdate(),INTERVAL 1 DAY), 'NOT_ESTIMATED', 'NOT_DONE', 0, 'BETA',curdate(),'NOT_ASSIGNED', 1, 'BUGFIXING');

insert into tasks (description,end_date,estimation_status,execution_status,executorid,project_name,start_date,status,story_points,task_type)
values ('..add description..', DATE_ADD(curdate(),INTERVAL 1 DAY), 'NOT_ESTIMATED', 'NOT_DONE', 0, 'BETA',curdate(),'NOT_ASSIGNED', 1, 'BUGFIXING');

insert into tasks (description,end_date,estimation_status,execution_status,executorid,project_name,start_date,status,story_points,task_type)
values ('..add description..', DATE_ADD(curdate(),INTERVAL 1 DAY), 'NOT_ESTIMATED', 'NOT_DONE', 0, 'BETA',curdate(),'NOT_ASSIGNED', 2, 'DEVELOPMENT');

insert into tasks (description,end_date,estimation_status,execution_status,executorid,project_name,start_date,status,story_points,task_type)
values ('..add description..', DATE_ADD(curdate(),INTERVAL 1 DAY), 'NOT_ESTIMATED', 'NOT_DONE', 0, 'BETA',curdate(),'NOT_ASSIGNED', 3, 'DEVELOPMENT');

insert into tasks (description,end_date,estimation_status,execution_status,executorid,project_name,start_date,status,story_points,task_type)
values ('..add description..', DATE_ADD(curdate(),INTERVAL 4 DAY), 'NOT_ESTIMATED', 'NOT_DONE', 0, 'BETA',curdate(),'NOT_ASSIGNED', 4, 'DEVELOPMENT');

insert into tasks (description,end_date,estimation_status,execution_status,executorid,project_name,start_date,status,story_points,task_type)
values ('..add description..', DATE_ADD(curdate(),INTERVAL 8 DAY), 'NOT_ESTIMATED', 'NOT_DONE', 0, 'BETA',curdate(),'NOT_ASSIGNED', 4, 'DEVELOPMENT');

insert into tasks (description,end_date,estimation_status,execution_status,executorid,project_name,start_date,status,story_points,task_type)
values ('..add description..', DATE_ADD(curdate(),INTERVAL 7 DAY), 'NOT_ESTIMATED', 'NOT_DONE', 0, 'BETA',curdate(),'NOT_ASSIGNED', 4, 'DEVELOPMENT');

insert into tasks (description,end_date,estimation_status,execution_status,executorid,project_name,start_date,status,story_points,task_type)
values ('..add description..', DATE_ADD(curdate(),INTERVAL 8 DAY), 'NOT_ESTIMATED', 'NOT_DONE', 0, 'BETA',curdate(),'NOT_ASSIGNED', 3, 'DEVELOPMENT');

insert into tasks (description,end_date,estimation_status,execution_status,executorid,project_name,start_date,status,story_points,task_type)
values ('..add description..', DATE_ADD(curdate(),INTERVAL 11 DAY), 'NOT_ESTIMATED', 'NOT_DONE', 0, 'BETA',curdate(),'NOT_ASSIGNED', 3, 'DEVELOPMENT');

insert into tasks (description,end_date,estimation_status,execution_status,executorid,project_name,start_date,status,story_points,task_type)
values ('..add description..', DATE_ADD(curdate(),INTERVAL 12 DAY), 'NOT_ESTIMATED', 'NOT_DONE', 0, 'BETA',curdate(),'NOT_ASSIGNED', 3, 'DEVELOPMENT');

insert into tasks (description,end_date,estimation_status,execution_status,executorid,project_name,start_date,status,story_points,task_type)
values ('..add description..', DATE_ADD(curdate(),INTERVAL 10 DAY), 'NOT_ESTIMATED', 'NOT_DONE', 0, 'BETA',curdate(),'NOT_ASSIGNED', 4, 'DEVELOPMENT');

insert into tasks (description,end_date,estimation_status,execution_status,executorid,project_name,start_date,status,story_points,task_type)
values ('..add description..', DATE_ADD(curdate(),INTERVAL 3 DAY), 'NOT_ESTIMATED', 'NOT_DONE', 0, 'BETA',curdate(),'NOT_ASSIGNED', 3, 'DEVELOPMENT');

insert into tasks (description,end_date,estimation_status,execution_status,executorid,project_name,start_date,status,story_points,task_type)
values ('..add description..', DATE_ADD(curdate(),INTERVAL 3 DAY), 'NOT_ESTIMATED', 'NOT_DONE', 0, 'BETA',curdate(),'NOT_ASSIGNED', 2, 'DEVELOPMENT');

insert into tasks (description,end_date,estimation_status,execution_status,executorid,project_name,start_date,status,story_points,task_type)
values ('..add description..', DATE_ADD(curdate(),INTERVAL 3 DAY), 'NOT_ESTIMATED', 'NOT_DONE', 0, 'BETA',curdate(),'NOT_ASSIGNED', 2, 'DEVELOPMENT');

insert into tasks (description,end_date,estimation_status,execution_status,executorid,project_name,start_date,status,story_points,task_type)
values ('..add description..', DATE_ADD(curdate(),INTERVAL 5 DAY), 'NOT_ESTIMATED', 'NOT_DONE', 0, 'BETA',curdate(),'NOT_ASSIGNED', 3, 'DEVELOPMENT');

insert into tasks (description,end_date,estimation_status,execution_status,executorid,project_name,start_date,status,story_points,task_type)
values ('..add description..', DATE_ADD(curdate(),INTERVAL 5 DAY), 'NOT_ESTIMATED', 'NOT_DONE', 0, 'BETA',curdate(),'NOT_ASSIGNED', 2, 'REFACTORING');

insert into tasks (description,end_date,estimation_status,execution_status,executorid,project_name,start_date,status,story_points,task_type)
values ('..add description..', DATE_ADD(curdate(),INTERVAL 5 DAY), 'NOT_ESTIMATED', 'NOT_DONE', 0, 'BETA',curdate(),'NOT_ASSIGNED', 2, 'REFACTORING');

insert into tasks (description,end_date,estimation_status,execution_status,executorid,project_name,start_date,status,story_points,task_type)
values ('..add description..', DATE_ADD(curdate(),INTERVAL 5 DAY), 'NOT_ESTIMATED', 'NOT_DONE', 0, 'BETA',curdate(),'NOT_ASSIGNED', 2, 'REFACTORING');

insert into tasks (description,end_date,estimation_status,execution_status,executorid,project_name,start_date,status,story_points,task_type)
values ('..add description..', DATE_ADD(curdate(),INTERVAL 5 DAY), 'NOT_ESTIMATED', 'NOT_DONE', 0, 'BETA',curdate(),'NOT_ASSIGNED', 2, 'REFACTORING');

insert into tasks (description,end_date,estimation_status,execution_status,executorid,project_name,start_date,status,story_points,task_type)
values ('..add description..', DATE_ADD(curdate(),INTERVAL 5 DAY), 'NOT_ESTIMATED', 'NOT_DONE', 0, 'BETA',curdate(),'NOT_ASSIGNED', 1, 'REFACTORING');

insert into tasks (description,end_date,estimation_status,execution_status,executorid,project_name,start_date,status,story_points,task_type)
values ('..add description..', DATE_ADD(curdate(),INTERVAL 5 DAY), 'NOT_ESTIMATED', 'NOT_DONE', 0, 'BETA',curdate(),'NOT_ASSIGNED', 3, 'REFACTORING');

insert into tasks (description,end_date,estimation_status,execution_status,executorid,project_name,start_date,status,story_points,task_type)
values ('..add description..', DATE_ADD(curdate(),INTERVAL 5 DAY), 'NOT_ESTIMATED', 'NOT_DONE', 0, 'BETA',curdate(),'NOT_ASSIGNED', 3, 'REFACTORING');
