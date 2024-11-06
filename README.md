# Praceando - Aplicativo Móvel

## Descrição

O *Praceando* é um aplicativo móvel desenvolvido em Java, com o objetivo de incentivar a participação em eventos sustentáveis em espaços públicos, promovendo o uso consciente das cidades e a preservação ambiental. A plataforma permite aos usuários explorar, participar e divulgar eventos, além de acessar recursos personalizados e funcionalidades gamificadas, tudo em prol de um estilo de vida mais sustentável.

## Funcionalidades do Aplicativo

1. **Home e Feed Sustentável**
   - Exibição semanal de frases que incentivam a sustentabilidade e a preservação ambiental.
   - Divulgação de eventos com filtros por categorias (esporte, alimentação, música, literatura, etc.).
   - Tela de detalhes dos eventos com informações como local, tags, data, hora, avaliações, fotos, quantidade de interessados e descrição. 
   - Usuários podem visualizar e avaliar eventos, e anunciantes têm a opção de criar e gerenciar eventos próprios.

2. **Calendário Personalizado**
   - Exibe os eventos que ocorrerão ou ocorreram em datas específicas, incluindo uma aba para anotações personalizadas.
   - Notificações automáticas para lembrar sobre os eventos adicionados pelo usuário.

3. **Mapa Interativo**
   - Exibição de mapa com marcadores personalizados para eventos e locais de interesse, além de geolocalização atual do usuário.
   - Integração com a Google Maps API para navegação interativa.

4. **Marketplace**
   - Exibe produtos disponíveis para compra, incluindo planos premium e avatares customizáveis para os usuários.

5. **Perfil do Usuário**
   - Permite ao usuário editar suas informações, como bio, nome e avatar.
   - Usuários premium ou anunciantes têm acesso a uma área restrita com informações adicionais.

6. **Cadastro e Login**
   - Gerenciamento de autenticação via Firebase para login seguro com e-mail e senha.

7. **Gamificação e Pontuação**
   - Sistema de pontuação onde os usuários ganham "pólen" ao participar de eventos.
   - Ranking baseado na quantidade de pólen coletado, incentivando o engajamento em eventos sustentáveis.

## Objetivo

O Praceando visa estimular o uso de espaços públicos e promover a sustentabilidade por meio de uma experiência interativa e educativa. Inspirado no Objetivo de Desenvolvimento Sustentável (ODS) 11 da ONU, o app incentiva cidades inclusivas, seguras, resilientes e sustentáveis.

## Tecnologias Utilizadas

- **Java**: Linguagem de programação principal do aplicativo.
- **Android SDK**: Ferramentas de desenvolvimento para Android.
- **Firebase**:
   - **Firestore Database**: Armazena anotações, URLs de fotos dos eventos, inventário de avatares e controle de avatar dos usuários.
   - **Authentication**: Controle de autenticação e acesso via e-mail e senha.
   - **Storage**: Armazena fotos de eventos e avatares, organizados em pastas por ID do evento.
- **Retrofit**: Biblioteca para consumo de APIs, como MongoDB, PostgreSQL e Flask (para IA de recomendação).
- **Google Maps API**: Exibição de mapas com marcadores personalizados e integração com a localização do usuário.

### Passos para Configuração

1. Clone o repositório do projeto:
   ```bash
   git clone https://github.com/praceando/praceando-app.git
   ```
2. Abra o projeto no Android Studio.
3. Sincronize as dependências.
4. Configure o Firebase no projeto seguindo [as instruções oficiais](https://firebase.google.com/docs/android/setup).
5. Configure a Google Maps API, caso ainda não tenha feito.
6. Execute o aplicativo em um dispositivo ou emulador Android.

## Equipe de Desenvolvimento

Desenvolvido com ❤️ pela equipe de desenvolvimento Praceando:
- [Camilla Ucci](https://github.com/millaUcci)
- [Mayla Renze](https://github.com/mayren-07)
