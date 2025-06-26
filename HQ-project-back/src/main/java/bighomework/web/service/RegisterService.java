package bighomework.web.service;

import bighomework.web.front.register.Register_Company;
import bighomework.web.front.register.Register_Executor;
import bighomework.web.front.register.Register_Teacher;

public interface RegisterService {
    void addTeacher(Register_Teacher r_teacher);

    void addCompany(Register_Company r_company);

    void addExecutor(Register_Executor r_executor);
}
