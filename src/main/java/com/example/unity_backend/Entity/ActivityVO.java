package com.example.unity_backend.Entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ActivityVO extends Activity implements Serializable {
    private static final long serialVersionUID = 1L;
    List<Reword> reward=new ArrayList<>();
    private String username;
    private String ActTotalProgress;
    private String UserCurrentProgress;
    private String hasGetReward;
}
