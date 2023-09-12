package project.listWrapper;

import project.entity.MailFile;

import java.util.List;

public class EmailForm {
    private List<MailFile> mailFilesList;

    public List<MailFile> getMailFilesList() {
        return mailFilesList;
    }

    public void setMailFilesList(List<MailFile> mailFilesList) {
        this.mailFilesList = mailFilesList;
    }
}
