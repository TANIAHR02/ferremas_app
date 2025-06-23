package cl.duoc.ferremasapp.controller;

import cl.duoc.ferremasapp.dto.PagoResponseDTO;
import cl.duoc.ferremasapp.service.WebPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class WebPayResultController {

    @Autowired
    private WebPayService webPayService;

    @GetMapping("/api/webpay/return")
    public String mostrarResultadoPago(@RequestParam(value = "token_ws", required = false) String token,
                                       @RequestParam(value = "TBK_TOKEN", required = false) String tbkToken,
                                       Model model) {
        if (token != null) {
            PagoResponseDTO estado = webPayService.consultarEstadoTransaccion(token);
            if (estado.getError() == null) {
                model.addAttribute("status", "success");
                model.addAttribute("token", token);
                model.addAttribute("monto", estado.getMonto());
                model.addAttribute("orden", estado.getOrdenCompra());
            } else {
                model.addAttribute("status", "fail");
                model.addAttribute("error", estado.getError());
            }
        } else {
            model.addAttribute("status", "fail");
            model.addAttribute("token", tbkToken != null ? tbkToken : "-");
        }
        return "webpay-resultado";
    }
} 