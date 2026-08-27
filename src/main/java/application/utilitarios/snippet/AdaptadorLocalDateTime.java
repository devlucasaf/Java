package application.utilitarios.snippet;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.LocalDateTime;

public class AdaptadorLocalDateTime implements JsonSerializer<LocalDateTime>, JsonDeserializer<LocalDateTime> {

    @Override
    public JsonElement serialize(LocalDateTime data, Type tipo, JsonSerializationContext contexto) {
        return new JsonPrimitive(data.toString());
    }

    @Override
    public LocalDateTime deserialize(JsonElement elemento, Type tipo, JsonDeserializationContext contexto) throws JsonParseException {
        try {
            return LocalDateTime.parse(elemento.getAsString());
        } catch (Exception excecao) {
            throw new JsonParseException("Data e hora inválidas.", excecao);
        }
    }
}
