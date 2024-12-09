package com.example.tovar_hisob_kitobi_mvc.implementation.messaging_queue;

import com.example.tovar_hisob_kitobi_mvc.implementation.prixod.model.dto.PrixodTovarRequestDTO;
import com.example.tovar_hisob_kitobi_mvc.implementation.rasxod.model.dto.RasxodTovarRequestDTO;
import com.example.tovar_hisob_kitobi_mvc.implementation.tovar.model.dto.TovarRequestDTO;
import com.example.tovar_hisob_kitobi_mvc.implementation.user.model.dto.UserRequestDTO;
import com.example.tovar_hisob_kitobi_mvc.implementation.vozvrat.model.dto.VozvratTovarRequestDTO;

public class Publisher {
    public void publishTovar(TovarRequestDTO requestDTO){
    }

    public void publishPrixodTovar(PrixodTovarRequestDTO requestDTO){
    }

    public void publishRasxodTovar(RasxodTovarRequestDTO requestDTO){
    }

    public void publishVozvratTovar(VozvratTovarRequestDTO requestDTO){
    }

    public void publishUser(UserRequestDTO requestDTO){
    }
}
