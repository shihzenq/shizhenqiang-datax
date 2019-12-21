package com.wugui.dataxweb.dto.job;

import com.wugui.dataxweb.dto.SearchDTO;
import lombok.Data;

@Data
public class JobManagerDTO extends SearchDTO {

    private String jobName;

    private String groupName;

    private Long groupId;
}
