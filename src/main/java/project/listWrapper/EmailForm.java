package project.listWrapper;

import project.entity.MailFiles;

import java.util.List;

public class EmailForm {
    private List<MailFiles> mailFilesList;

    public List<MailFiles> getMailFilesList() {
        return mailFilesList;
    }

    public void setMailFilesList(List<MailFiles> mailFilesList) {
        this.mailFilesList = mailFilesList;
    }
}
