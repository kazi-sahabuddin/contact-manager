package com.sahabuddin.contactmanager.utilities;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Slf4j
@Component
public class SessionUtils {

    public void removeAttributeFromSession(String attributeName) {
        try{
            log.debug("Removing attribute {} from session", attributeName);
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (attributes != null) {
                HttpServletRequest request = attributes.getRequest();
                HttpSession session = request.getSession();
                session.removeAttribute(attributeName);
            }

        } catch (Exception e){
            log.error(e.getMessage());

        }

    }
}
