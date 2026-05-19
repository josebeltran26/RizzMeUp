// =============================================================================
// Script de inicializacion de la BD rizzmeup para MongoDB Shell (mongosh)
// Ejecutar con: mongosh < init_rizzmeup.js
// =============================================================================

// Seleccionar / crear la base de datos
use('rizzmeup');

print("=== Creando base de datos rizzmeup ===");

// =============================================================================
// Crear colecciones con validacion de esquema
// =============================================================================

// --- usuarios ---
db.createCollection("usuarios", {
  validator: {
    $jsonSchema: {
      bsonType: "object",
      required: ["_id", "correo", "nombre", "activo"],
      properties: {
        _id:               { bsonType: "long",   description: "ID auto-incrementado" },
        correo:            { bsonType: "string",  description: "Correo unico del usuario" },
        contrasena:        { bsonType: "string" },
        nombre:            { bsonType: "string" },
        edad:              { bsonType: "int" },
        profesion:         { bsonType: "string" },
        descripcionPersonal: { bsonType: "string" },
        ciudad:            { bsonType: "string" },
        genero:            { bsonType: "string" },
        activo:            { bsonType: "bool" },
        fechaNacimiento:   { bsonType: "string" },
        fechaRegistro:     { bsonType: "string" }
      }
    }
  }
});
print("  [OK] Coleccion 'usuarios' creada");

// --- preferencias ---
db.createCollection("preferencias", {
  validator: {
    $jsonSchema: {
      bsonType: "object",
      required: ["usuarioId"],
      properties: {
        _id:          { bsonType: "long" },
        usuarioId:    { bsonType: "long" },
        generoBuscado:{ bsonType: "string" },
        edadMinima:   { bsonType: "int" },
        edadMaxima:   { bsonType: "int" },
        ciudadPreferida: { bsonType: "string" }
      }
    }
  }
});
print("  [OK] Coleccion 'preferencias' creada");

// --- likes ---
db.createCollection("likes", {
  validator: {
    $jsonSchema: {
      bsonType: "object",
      required: ["usuarioOrigenId", "usuarioDestinoId", "esLike"],
      properties: {
        _id:              { bsonType: "long" },
        usuarioOrigenId:  { bsonType: "long" },
        usuarioDestinoId: { bsonType: "long" },
        esLike:           { bsonType: "bool" },
        fechaLike:        { bsonType: "string" }
      }
    }
  }
});
print("  [OK] Coleccion 'likes' creada");

// --- matches ---
db.createCollection("matches", {
  validator: {
    $jsonSchema: {
      bsonType: "object",
      required: ["usuario1Id", "usuario2Id", "activo"],
      properties: {
        _id:        { bsonType: "long" },
        usuario1Id: { bsonType: "long" },
        usuario2Id: { bsonType: "long" },
        fechaMatch: { bsonType: "string" },
        activo:     { bsonType: "bool" }
      }
    }
  }
});
print("  [OK] Coleccion 'matches' creada");

// --- mensajes ---
db.createCollection("mensajes", {
  validator: {
    $jsonSchema: {
      bsonType: "object",
      required: ["matchId", "remitenteId", "destinatarioId", "contenido"],
      properties: {
        _id:            { bsonType: "long" },
        matchId:        { bsonType: "long" },
        remitenteId:    { bsonType: "long" },
        destinatarioId: { bsonType: "long" },
        contenido:      { bsonType: "string" },
        fechaEnvio:     { bsonType: "string" },
        leido:          { bsonType: "bool" }
      }
    }
  }
});
print("  [OK] Coleccion 'mensajes' creada");

// --- sequences (auto-increment de IDs) ---
db.createCollection("sequences");
print("  [OK] Coleccion 'sequences' creada");

// =============================================================================
// Indices para mejorar rendimiento de busquedas frecuentes
// =============================================================================
print("\n=== Creando indices ===");

// usuarios: busqueda por correo (login) y por ciudad/genero (candidatos)
db.usuarios.createIndex({ correo: 1 }, { unique: true, name: "idx_correo_unico" });
db.usuarios.createIndex({ activo: 1 }, { name: "idx_activo" });
db.usuarios.createIndex({ ciudad: 1, genero: 1, activo: 1 }, { name: "idx_ciudad_genero_activo" });
db.usuarios.createIndex({ edad: 1 }, { name: "idx_edad" });
print("  [OK] Indices en 'usuarios'");

// preferencias: busqueda por usuarioId
db.preferencias.createIndex({ usuarioId: 1 }, { unique: true, name: "idx_preferencias_usuario" });
print("  [OK] Indices en 'preferencias'");

// likes: busqueda rapida de si ya hubo swipe entre dos usuarios
db.likes.createIndex({ usuarioOrigenId: 1 }, { name: "idx_likes_origen" });
db.likes.createIndex({ usuarioOrigenId: 1, usuarioDestinoId: 1 }, { unique: true, name: "idx_likes_par_unico" });
print("  [OK] Indices en 'likes'");

// matches: busqueda por ambos usuarios
db.matches.createIndex({ usuario1Id: 1, usuario2Id: 1 }, { name: "idx_match_par" });
db.matches.createIndex({ usuario1Id: 1, activo: 1 }, { name: "idx_match_u1" });
db.matches.createIndex({ usuario2Id: 1, activo: 1 }, { name: "idx_match_u2" });
print("  [OK] Indices en 'matches'");

// mensajes: busqueda por match y por no leidos
db.mensajes.createIndex({ matchId: 1, fechaEnvio: 1 }, { name: "idx_mensajes_match_fecha" });
db.mensajes.createIndex({ matchId: 1, destinatarioId: 1, leido: 1 }, { name: "idx_mensajes_no_leidos" });
print("  [OK] Indices en 'mensajes'");

// =============================================================================
// Inicializar contadores de sequences en 0
// =============================================================================
print("\n=== Inicializando contadores de ID ===");

db.sequences.insertMany([
  { _id: "usuarios",    valor: NumberLong(0) },
  { _id: "preferencias",valor: NumberLong(0) },
  { _id: "likes",       valor: NumberLong(0) },
  { _id: "matches",     valor: NumberLong(0) },
  { _id: "mensajes",    valor: NumberLong(0) }
]);
print("  [OK] Contadores inicializados");

// =============================================================================
// Datos de prueba — 3 usuarios iniciales
// =============================================================================
print("\n=== Insertando datos de prueba ===");

// Actualizar contador de usuarios a 3
db.sequences.updateOne({ _id: "usuarios" }, { $set: { valor: NumberLong(3) } });

db.usuarios.insertMany([
  {
    _id: NumberLong(1),
    correo: "valentina@rizzmeup.com",
    contrasena: "1234",
    nombre: "Valentina",
    edad: 21,
    profesion: "Arquitectura",
    descripcionPersonal: "Me gusta el cafe, los museos y los atardeceres.",
    ciudad: "Guadalajara",
    fotoPerfilBase64: "",
    genero: "Femenino",
    fechaNacimiento: "2004-03-15",
    fechaRegistro: "2026-05-13T09:00:00",
    activo: true,
    escuela: "UDG",
    nivelEstudios: "Universidad",
    signoZodiacal: "Piscis",
    redSocialInstagram: "@vale.arq",
    redSocialTiktok: "",
    estacionFavorita: "Otoño",
    videojuegoFavorito: "Stardew Valley",
    peliculaFavorita: "Inception",
    serieFavorita: "Stranger Things",
    cancionFavorita: "Flowers",
    artistaFavorito: "Miley Cyrus",
    comidaFavorita: "Sushi",
    bebidaFavorita: "Matcha latte",
    animalFavorito: "Gato",
    colorFavorito: "Terracota",
    emojiFavorito: "🌸",
    frasePersonal: "Vivir lento pero bonito.",
    planIdealCita: "Museo + cafe + paseo",
    tipoMascota: "Gato",
    superpoder: "Teletransportacion",
    deporteFavorito: "Yoga",
    lugarSonado: "Kyoto",
    generosMusicalFavoritos: ["POP", "INDIE", "CLASICA"],
    hobbies: ["LEER", "FOTOGRAFIA", "YOGA"],
    materiasFavoritas: []
  },
  {
    _id: NumberLong(2),
    correo: "marcos@rizzmeup.com",
    contrasena: "1234",
    nombre: "Marcos",
    edad: 23,
    profesion: "Mesero",
    descripcionPersonal: "Me gusta viajar por el mundo y tambien busca buena onda con mis amigos.",
    ciudad: "Guadalajara",
    fotoPerfilBase64: "",
    genero: "Masculino",
    fechaNacimiento: "2002-08-22",
    fechaRegistro: "2026-05-13T09:01:00",
    activo: true,
    escuela: "CUCEI",
    nivelEstudios: "Universidad",
    signoZodiacal: "Leo",
    redSocialInstagram: "@marcos.viajero",
    redSocialTiktok: "@marcosv",
    estacionFavorita: "Verano",
    videojuegoFavorito: "FIFA",
    peliculaFavorita: "Interstellar",
    serieFavorita: "Breaking Bad",
    cancionFavorita: "Blinding Lights",
    artistaFavorito: "The Weeknd",
    comidaFavorita: "Tacos",
    bebidaFavorita: "Agua de jamaica",
    animalFavorito: "Perro",
    colorFavorito: "Azul marino",
    emojiFavorito: "🌍",
    frasePersonal: "El mundo es tuyo si te atreves.",
    planIdealCita: "Concierto + cenar afuera",
    tipoMascota: "Perro",
    superpoder: "Volar",
    deporteFavorito: "Futbol",
    lugarSonado: "Tailandia",
    generosMusicalFavoritos: ["POP", "REGGAETON", "HIP_HOP"],
    hobbies: ["VIAJAR", "DEPORTES", "CINE"],
    materiasFavoritas: []
  },
  {
    _id: NumberLong(3),
    correo: "sofia@rizzmeup.com",
    contrasena: "1234",
    nombre: "Sofia",
    edad: 22,
    profesion: "Diseño Grafico",
    descripcionPersonal: "Amante del arte, el anime y los dias de lluvia.",
    ciudad: "CDMX",
    fotoPerfilBase64: "",
    genero: "Femenino",
    fechaNacimiento: "2003-11-30",
    fechaRegistro: "2026-05-13T09:02:00",
    activo: true,
    escuela: "UNAM",
    nivelEstudios: "Universidad",
    signoZodiacal: "Sagitario",
    redSocialInstagram: "@sofi.design",
    redSocialTiktok: "@sofidsgn",
    estacionFavorita: "Invierno",
    videojuegoFavorito: "Genshin Impact",
    peliculaFavorita: "Your Name",
    serieFavorita: "Attack on Titan",
    cancionFavorita: "Renai Circulation",
    artistaFavorito: "YOASOBI",
    comidaFavorita: "Ramen",
    bebidaFavorita: "Boba tea",
    animalFavorito: "Zorro",
    colorFavorito: "Morado",
    emojiFavorito: "🎨",
    frasePersonal: "El arte salva almas.",
    planIdealCita: "Galeria de arte + ramen",
    tipoMascota: "Conejo",
    superpoder: "Crear mundos con imaginacion",
    deporteFavorito: "Natacion",
    lugarSonado: "Japon",
    generosMusicalFavoritos: ["KPOP", "INDIE", "ELECTRONICA"],
    hobbies: ["ANIME", "DIBUJO", "VIDEOJUEGOS", "MANGA"],
    materiasFavoritas: []
  }
]);

print("  [OK] 3 usuarios de prueba insertados");
print("\n=== Base de datos rizzmeup lista ===");
print("Colecciones: " + db.getCollectionNames().join(", "));
print("Usuarios: " + db.usuarios.countDocuments());
