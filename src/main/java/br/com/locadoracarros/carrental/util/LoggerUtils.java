package br.com.locadoracarros.carrental.util;

import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.atomic.AtomicReference;

public abstract class LoggerUtils {

	private static final Logger logger = LogManager.getLogger();

	public static String calculateExecutionTime(long startTimeInMillis) {
		return generateTextCalculationType(System.currentTimeMillis() - startTimeInMillis);
	}

	public static String generateTextCalculationType(long time) {

		AtomicReference<StringBuilder> sb = new AtomicReference<>(new StringBuilder());

		sb.get().append("O tempo de Execução foi de ");
		sb.get().append(time);
		sb.get().append(" ms.");

		return sb.toString();
	}

	public static String notificationEndpointRequested(String methodRequest, String endPointCollection) {
		return notificationEndpointRequested(methodRequest, endPointCollection, "", "");
	}

	public static String notificationEndpointRequested(String methodRequest, String endPointCollection, String subEndPoint) {
		return notificationEndpointRequested(methodRequest, endPointCollection, subEndPoint, "");
	}
	public static String notificationEndpointRequested(String methodRequest, String endPointCollection,
	                                                   String subEndPoint, String specialParams) {

		if (!subEndPoint.trim().startsWith("/") && !subEndPoint.isEmpty()) subEndPoint = "/"+ subEndPoint;
		if (!specialParams.trim().startsWith("?") && !specialParams.isEmpty()) specialParams = "?"+ subEndPoint;

		StringBuilder sb = new StringBuilder();
		sb.append(" [ ");
		sb.append(methodRequest.trim().toUpperCase());
		sb.append(" ] => { ");
		sb.append(endPointCollection.trim());
		if (!subEndPoint.isEmpty())
			sb.append(subEndPoint.trim());
		if (!specialParams.isEmpty()) {
			sb.append(specialParams.trim());
		}
		sb.append(" } ");

		return sb.toString();
	}

	public static String lineSplitter() {
		return "------------------------------------------------------";
	}

	public static void printStackTrace(Exception e) {
		printStackTrace(e, false, null);
	}
	public static void printStackTrace(Exception e, boolean onlyProject) {
		printStackTrace(e, onlyProject, null);
	}

	public static void printStackTrace(Exception e, boolean onlyProject, Object o) {

		StringBuilder sb = new StringBuilder();
		if (o != null) {
			sb.append("O obj : " + new Gson().toJson(o) + " causou um problema : \n\n");
		}
		sb.append(e.getMessage());
		for (StackTraceElement s : e.getStackTrace()) {
			if (!onlyProject || (onlyProject && s.toString()
					.contains("br.com.pontosistemas.portalfinanceirogranviverws"))) {

				sb.append("\n");
				sb.append("\t");
				sb.append("\t");
				sb.append(" ");
				sb.append(s);
			}
		}

		logger.error(sb.toString());
	}
}