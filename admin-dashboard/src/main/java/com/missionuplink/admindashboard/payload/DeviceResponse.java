package com.missionuplink.admindashboard.payload;

import com.missionuplink.admindashboard.model.entity.Device;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DeviceResponse {
    private List<Device> content;
    private int pageNo;
    private int pageSize;
    private long totalDevices;
    private int totalPages;
    private boolean lastPage;
}
