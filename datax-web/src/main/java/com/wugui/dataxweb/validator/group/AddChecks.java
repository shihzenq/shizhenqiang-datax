package com.wugui.dataxweb.validator.group;

import javax.validation.GroupSequence;
import javax.validation.groups.Default;

@GroupSequence({Default.class, Slot1AfterDefault.class, Cheap.class,OnAddCheap.class, OnAdd.class, Expensive.class})
public interface AddChecks {
}
