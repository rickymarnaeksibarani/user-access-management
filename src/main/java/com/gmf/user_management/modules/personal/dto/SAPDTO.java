package com.gmf.user_management.modules.personal.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SAPDTO {

        private String uid;
        private List<String> businessUnitCodes;
        private List<String> businessUnitDescriptions;
        private List<String> sapLoginTypes;
        private List<String> licenseTypes;
        private List<String> jobPositions;
}
