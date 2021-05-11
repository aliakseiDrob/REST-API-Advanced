package com.epam.esm.dao.audit;

import com.epam.esm.audit.Audit;

public interface AuditDao {
    void save (Audit audit);
}
