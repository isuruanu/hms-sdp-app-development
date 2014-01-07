package hms.kite.app.ussd.service;

import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import hms.kite.app.ussd.menu.UssdMenu;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static hms.kite.app.ussd.util.ConfigUtil.*;
import static hms.kite.samples.api.ussd.OperationType.*;

public class UssdMenuResolver {

    private final UssdSessionRepo sessionRepo;
    private final String exitMessage;
    private final String backMessage;
    private final List<String> reservedMessages;
    private final Pattern ussdMessagePattern;

    public UssdMenuResolver() {

        this.sessionRepo = new InMemoryUssdSessionRepo();
        exitMessage = getApplicationConfig("exit.option");
        backMessage = getApplicationConfig("back.option");

        reservedMessages = Lists.newArrayList(exitMessage, backMessage);
        final String ussdMesPatternString = getApplicationConfig("ussd.message.pattern");
        ussdMessagePattern = Pattern.compile(ussdMesPatternString);
    }

    public UssdMenu resolve(String sessionId, String moMessage) {
        final Optional<UssdMenu> ussdMenu = sessionRepo.getUssdMenu(sessionId);
        UssdMenu nextUssdMenu;
        if(!ussdMenu.isPresent()) {
            nextUssdMenu = getWelcomeUssdMenu(sessionId);
        } else {
            if(exitMessage.equals(moMessage)) {
                nextUssdMenu = getExitUssdMenu(sessionId);
            }
            else if(backMessage.equals(moMessage)) {
                nextUssdMenu = getPreviousUssdMenu(ussdMenu);
            }
            else {
                nextUssdMenu = getUssdMenu(ussdMenu, Optional.of(getNextMenuId(moMessage, ussdMenu.get())));
            }
        }
        sessionRepo.upsertSession(sessionId, nextUssdMenu);
        return nextUssdMenu;
    }

    private UssdMenu getPreviousUssdMenu(Optional<UssdMenu> ussdMenu) {
        final Optional<String> previousMenuId = getPreviousMenuId(ussdMenu.get());
        return getUssdMenu(ussdMenu, previousMenuId);
    }

    private UssdMenu getUssdMenu(Optional<UssdMenu> ussdMenu, Optional<String> nextMenuId) {
        if(nextMenuId.isPresent()) {
            final Optional<String> nextMessage = getMessage(nextMenuId.get());
            if(!nextMessage.isPresent()) {
                return new UssdMenu(ussdMenu.get().getMenuId(), getMessage("invalid.input.message").get(), MT_CONT);
            }
            final UssdMenu previousMenu = new UssdMenu(nextMenuId.get(), nextMessage.get(), MT_CONT);
            return previousMenu;
        } else {
            return new UssdMenu(ussdMenu.get().getMenuId(), getMessage("invalid.input.message").get(), MT_CONT);
        }
    }

    private UssdMenu getWelcomeUssdMenu(String sessionId) {
        final String welcomeMessageId = "1";
        final Optional<String> welcomeMessage = getMessage(welcomeMessageId);
        final UssdMenu welcomeScreen = new UssdMenu(welcomeMessageId, welcomeMessage.get(), MT_CONT);
        return welcomeScreen;
    }

    private UssdMenu getExitUssdMenu(String sessionId) {
        final String exitMessageId = "exit.message";
        final Optional<String> exitMessage = getMessage(exitMessageId);
        final UssdMenu exitScreen = new UssdMenu(exitMessageId, exitMessage.get(), MT_FIN);
        try {
            sessionRepo.removeSession(sessionId);
        } catch (UssdSessionNotFoundException e) {
            e.printStackTrace();
        }
        return exitScreen;
    }

    private String getNextMenuId(String moMessage, UssdMenu currentUssdMenu) {
        return currentUssdMenu.getMenuId() + "." + moMessage;
    }

    private Optional<String> getPreviousMenuId(UssdMenu currentUssdMenu) {

        if(getMessage("invalid.input.message").get().equals(currentUssdMenu.getMessage())) {
            return Optional.of(currentUssdMenu.getMenuId());
        }

        final Matcher matcher = ussdMessagePattern.matcher(currentUssdMenu.getMenuId());
        if(!matcher.matches()) {
            return Optional.absent();
        } else {
            return Optional.fromNullable(matcher.group("prvMenuId"));
        }
    }
}
