package com.example.tovar_hisob_kitobi_mvc.implementation.messaging_queue;

import com.example.tovar_hisob_kitobi_mvc.implementation.prixod.model.dto.PrixodTovarRequestDTO;
import com.example.tovar_hisob_kitobi_mvc.implementation.prixod.service.PrixodTovarService;
import com.example.tovar_hisob_kitobi_mvc.implementation.rasxod.model.dto.RasxodTovarRequestDTO;
import com.example.tovar_hisob_kitobi_mvc.implementation.rasxod.service.RasxodTovarService;
import com.example.tovar_hisob_kitobi_mvc.implementation.tovar.model.dto.TovarRequestDTO;
import com.example.tovar_hisob_kitobi_mvc.implementation.tovar.service.TovarService;
import com.example.tovar_hisob_kitobi_mvc.implementation.user.model.dto.UserRequestDTO;
import com.example.tovar_hisob_kitobi_mvc.implementation.user.service.UserService;
import com.example.tovar_hisob_kitobi_mvc.implementation.vozvrat.model.dto.VozvratTovarRequestDTO;
import com.example.tovar_hisob_kitobi_mvc.implementation.vozvrat.service.VozvratTovarService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Consumer {
    private final TovarService tovarService;
    private final PrixodTovarService prixodTovarService;
    private final RasxodTovarService rasxodTovarService;
    private final VozvratTovarService vozvratTovarService;
    private final UserService userService;

    public void consumeTovar(TovarRequestDTO requestDTO){
        tovarService.create(requestDTO);
    }

    public void consumePrixodTovar(PrixodTovarRequestDTO requestDTO){
        prixodTovarService.create(requestDTO);
    }

    public void consumeRasxodTovar(RasxodTovarRequestDTO requestDTO){
        rasxodTovarService.create(requestDTO);
    }

    public void consumeVozvratTovar(VozvratTovarRequestDTO requestDTO){
        vozvratTovarService.create(requestDTO);
    }

    public void consumeUser(UserRequestDTO requestDTO){
        userService.create(requestDTO);
    }

}
