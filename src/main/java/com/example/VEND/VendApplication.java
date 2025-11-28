package com.example.VEND;

import com.example.VEND.config.JpaConfig;
import com.example.VEND.view.TelaLogin;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.example.VEND")
public class VendApplication {

	private static AnnotationConfigApplicationContext context;

	public static void main(String[] args) {
		// Inicializar Spring Context
		context = new AnnotationConfigApplicationContext(VendApplication.class, JpaConfig.class);

		// Obter bean específico do contexto - SEM @Autowired
		java.awt.EventQueue.invokeLater(() -> {
			TelaLogin telaLogin = context.getBean(TelaLogin.class);
			telaLogin.setVisible(true);

		});

		// Fechar contexto ao encerrar
		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			if (context != null && context.isActive()) {
				context.close();
			}
		}));
	}

	public static AnnotationConfigApplicationContext getContext() {
		return context;
	}

	public static <T> T getTela(Class<T> telaClass) {
		return context.getBean(telaClass);
	}
}
