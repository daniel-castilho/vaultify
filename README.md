# Vaultify - Gerador de Senhas Seguras

Vaultify é uma API REST simples para geração de senhas seguras usando caracteres aleatórios.

## Funcionalidades

- Geração de senhas seguras com tamanho personalizável
- Utiliza `SecureRandom` para geração de números aleatórios criptograficamente fortes
- Suporta todos os caracteres ASCII imprimíveis (32 a 126)

## Tecnologias

- Java 21
- Spring Boot 3.5.7
- Maven

## Como Executar

1. Certifique-se de ter o Java 21 e o Maven instalados
2. Clone o repositório
3. Navegue até o diretório do projeto
4. Execute o comando:
   ```bash
   ./mvnw spring-boot:run
   ```
5. A API estará disponível em `http://localhost:8080`

## Endpoints

### Gerar Senha

**POST** `/api/passwords`

**Exemplo de Requisição:**
```json
{
    "length": 12
}
```

**Exemplo de Resposta:**
```json
{
    "response": "kP9#mN2@xL7!"
}
```

## Exemplo de Uso com cURL

```bash
curl -X POST http://localhost:8080/api/passwords \
  -H "Content-Type: application/json" \
  -d '{"length": 16,
  "includeSpecialChars": true,
  "includeNumbers": true,
  "includeUppercase": true,
  "includeLowercase": true}'
```

## Licença

Este projeto está licenciado sob a licença MIT. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.
