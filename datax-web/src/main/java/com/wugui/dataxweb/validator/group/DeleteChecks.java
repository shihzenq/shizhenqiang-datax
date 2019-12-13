package com.wugui.dataxweb.validator.group;

import javax.validation.GroupSequence;
import javax.validation.groups.Default;

@GroupSequence({Default.class, Cheap.class, OnDelete.class, Expensive.class})
public interface DeleteChecks {
}
