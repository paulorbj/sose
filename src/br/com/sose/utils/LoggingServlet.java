package br.com.sose.utils;

import java.io.IOException;
import java.security.Principal;
import java.text.DateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;


public class LoggingServlet extends HttpServlet
{
    private static final long serialVersionUID = 4355325334781642242L;
    private static final Logger logger =
        Logger.getLogger(LoggingServlet.class);

    private static final String PARAM_LEVEL = "level";
    private static final String PARAM_CATEGORY = "category";
    private static final String PARAM_TIMESTAMP = "timestamp";
    private static final String PARAM_MESSAGE = "message";

    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException
    {
        process(req, resp);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException
    {
        process(req, resp);
    }

    protected void process(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException
    {
        try
        {
            handleRequest(request);
        }
        catch (Throwable throwable)
        {
            logger.debug("Error handling logging request", throwable);
        }
    }

    private void handleRequest(HttpServletRequest request)
    {
        Logger log = getLogger(request);
        if (log != null)
        {
            Level level = getLevel(request);
            String message = getMessage(request);
            log.log(level, message);
        }
        else
        {
            logger.error("Unable to get logger for request: " + request.getQueryString());
        }
    }

    private Logger getLogger(HttpServletRequest request)
    {
        String category = request.getParameter(PARAM_CATEGORY);
        if (category != null)
        {
            return Logger.getLogger(category);
        }
        return null;
    }

    private Level getLevel(HttpServletRequest request)
    {
        int level = 0;
        String levelStr = request.getParameter(PARAM_LEVEL);
        if (levelStr != null)
        {
            level = Integer.parseInt(levelStr);
        }
        switch (level)
        {
            case 2:     return Level.DEBUG;
            case 4:     return Level.INFO;
            case 6:     return Level.WARN;
            case 8:     return Level.ERROR;
            case 1000:  return Level.FATAL;
            default:
            case 0:     return Level.ALL;
        }
    }

    private String getMessage(HttpServletRequest request)
    {
        String timestamp = getTimestamp(request);
        String message = request.getParameter(PARAM_MESSAGE);

        StringBuffer buffer = new StringBuffer();
        if (timestamp != null)
        {
            buffer.append("[")
                  .append(timestamp)
                  .append("]: ");
        }
        buffer.append(message);
        return buffer.toString();
    }

    private String getTimestamp(HttpServletRequest request)
    {
        String timestamp = request.getParameter(PARAM_TIMESTAMP);
        if (timestamp != null)
        {
            Date date = new Date(Long.parseLong(timestamp));
            timestamp = DateFormat.getDateTimeInstance().format(date);
        }
        return timestamp;
    }
}
