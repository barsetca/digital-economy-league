# неделя 7

Итог:

* Написал AuthController и ProfileController DepartmentController, StaffUnitController с описаниями swagger и необходимые методы в сервисах и репозиториях 
* реализовал на фронте страницы: авторизации, профиля и профилей сотрудников, списка задач +  добавление новго сотрудника сотрудника (profile) во всплывающем окне
* профиксил ошибки приложения (названия столбцов, полей в db, убрал циклические ссылки) для корректной работы
* реализовал тесты для  StaffUnitRepository RoleRepository AuthController и ProfileController DepartmentController, StaffUnitController
- добавил поле visibleName в сущности Role и Priority с соответствующими Hibernate аннотациями.
- добавил поле visibleName в RoleDto и PriorityDto с соответствующими Swagger аннотациями.
- добавил поле visibleName в соответствующие таблицы DB
- заполнил таблицы данными для новых полей
- дополнил в тестовые классы RoleRepositoryTest, UserRepositoryTest, RoleServiceTest, UserServiceTest использование новых полей
* получил опыт анализа чужого кода
* получил опыт разруливания конфликтов с основной веткой через процедуру merge
* получил опыт командной разработки
  
Спасибо за курс!!!