package project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import project.entity.MailFile;
import project.entity.User;
import project.listWrapper.EmailForm;
import project.service.MailFilesService;
import project.service.MailSenderService;
import project.service.UserService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
public class MailSenderController {
    private final MailFilesService mailFilesService;
    private final UserService userService;
    private final MailSenderService mailSenderService;

    public MailSenderController(MailFilesService mailFilesService, UserService userService, MailSenderService mailSenderService) {
        this.mailFilesService = mailFilesService;
        this.userService = userService;
        this.mailSenderService = mailSenderService;
    }
    private String uploadPath = "/Users/Anastassia/IdeaProjects/Kino-CMS_admin/uploads";
    private Integer n = 9;
    private String userCheck = "";
    String emails = "";
    String usersNumber = "";
    @GetMapping("/admin/messages")
    public String showMailForm(Model model){
        EmailForm emailForm = new EmailForm();
        emailForm.setMailFilesList(mailFilesService.getAllMailFiles());
        model.addAttribute("emailForm",emailForm);
        model.addAttribute("pageNUm", n);
        model.addAttribute("userCheck", userCheck);
        model.addAttribute("users", userService.getUsersCount());
        model.addAttribute("usersNumber", usersNumber);
        return "emailSending/email_sending";
    }
    @PostMapping ("/admin/messages")
    public String getMailForm(@ModelAttribute("emailForm")EmailForm emailForm,@RequestParam(name="radioFile", required = false) Long radioFile,
                              @RequestParam("inputUser") String inputUser, @RequestParam("inputLetter") MultipartFile inputLetter,
                              @RequestParam("deleteInput") String deleteInput,Model model){
        String fileName = "";
        if(!inputLetter.getOriginalFilename().equals("")) {
            Path path = Paths.get(uploadPath + "/" + inputLetter.getOriginalFilename());
            try {
                inputLetter.transferTo(new File(path.toUri()));
            } catch (IOException e) {
            }
            fileName = inputLetter.getOriginalFilename();
            int size = mailFilesService.getAllMailFiles().size();
            if (size < 5) {
                MailFile mailFiles = new MailFile();
                mailFiles.setFile(inputLetter.getOriginalFilename());
                //emailForm.getMailFilesList().add(mailFiles);
                mailFilesService.saveMailFiles(mailFiles);
            }
            if (size >= 5){
                //delete = true;
                File file = new File(uploadPath+"/"+inputLetter.getOriginalFilename());
                file.delete();
            }

        } else if(radioFile != null){
            MailFile mailFiles = mailFilesService.getMailFileById(radioFile);
            fileName = mailFiles.getFile();
        }
        int i = 0;
        if(inputUser.equals("all")){
            emails = "";
            List<User> users = userService.getAllUsers();
            for(User user: users){
                if(i < users.size()-1){
                    emails+=user.getEmail()+", ";
                } else{
                    emails+=user.getEmail();
                }
            }
        }
        mailSenderService.sendEmail(emails,fileName);
        deleteMailFiles(deleteInput);
        userCheck = "";
        emails = "";
        usersNumber = "";
        return "redirect:/admin/messages";
    }
    @GetMapping("/admin/messages/users")
    public String showUsers(Model model){
        model.addAttribute("users",userService.getAllUsers());
        model.addAttribute("pageNUm", n);
        return "emailSending/choose_user";
    }
    @PostMapping ("/admin/messages/users")
    public String getUsers(@RequestParam("check") Long[] checks, Model model){
        //int i = 1;
        for(int i = 0; i<checks.length; i++){
            User user = userService.getUserById(checks[i]);
            if(i < checks.length-1){
                emails+=user.getEmail()+", ";
            } else{
                emails+=user.getEmail();
            }
            //i++;
        }
        System.out.println(checks.length);
        System.out.println(checks[0]);
        usersNumber = String.valueOf(checks.length);
        userCheck = "choose";
        return "redirect:/admin/messages";
    }

    private void deleteMailFiles(String fileIds){
        if(!fileIds.equals("")) {
            String[] ids = fileIds.split(",");
            for (int i = 0; i < ids.length; i++) {
                deleteFile(Long.valueOf(ids[i]));
                mailFilesService.deleteMailFileById(Long.valueOf(ids[i]));
            }
        }
    }

    private void deleteFile(Long id){
        MailFile mailFiles = mailFilesService.getMailFileById(id);
        File file = new File(uploadPath+"/"+mailFiles.getFile());
        file.delete();
    }
}
