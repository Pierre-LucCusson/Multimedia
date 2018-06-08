package com.example.plcus.multimedia;

import fi.iki.elonen.NanoHTTPD;

public abstract class ServerHTTPD extends NanoHTTPD {

    public ServerHTTPD()
    {
        super(8080);
    }

    @Override
    public Response serve(IHTTPSession session) {

        Response response;
        if (session.getMethod() == Method.GET) {
            String uri = session.getUri();
            String command = getCommand(uri);

            Song currentSong;

            switch (getCommand(uri)) {
                case ServerCommand.PLAY_OR_PAUSE:
                    currentSong = playOrPauseCommand();
                    response = newFixedLengthResponse(currentSong.toJson());
                    break;
                case ServerCommand.PLAY:
                    currentSong = playCommand();
                    response = newFixedLengthResponse(currentSong.toJson());
                    break;
                case ServerCommand.PAUSE:
                    currentSong = pauseCommand();
                    response = newFixedLengthResponse(currentSong.toJson());
                    break;
                case ServerCommand.STOP:
                    currentSong = stopCommand();
                    response = newFixedLengthResponse(currentSong.toJson());
                    break;
                case ServerCommand.PREVIOUS:
                    currentSong = previousCommand();
                    response = newFixedLengthResponse(currentSong.toJson());
                    break;
                case ServerCommand.NEXT:
                    currentSong = nextCommand();
                    response = newFixedLengthResponse(currentSong.toJson());
                    break;
                case ServerCommand.LOOP:
                    Boolean isLooping = loopCommand();
                    response = newFixedLengthResponse(ServerCommand.LOOP); //TODO send isLooping to the client
                    break;
                case ServerCommand.SHUFFLE:
                    Boolean isShuffled = shuffleCommand();
                    response = newFixedLengthResponse(ServerCommand.SHUFFLE); //TODO send isShuffled to client
                    break;
                case ServerCommand.SEEK:
                    seekToCommand(5000);
                    response = newFixedLengthResponse(ServerCommand.SEEK);
                    break;
                case ServerCommand.SONG:
                    response = newFixedLengthResponse(getCurrentSong().toJson());
                    break;
                default:
                    response = newFixedLengthResponse(command + " " + ServerCommand.DOES_NOT_EXIST);
                    break;
            }
        }
        else {
            response = newFixedLengthResponse(ServerCommand.DOES_NOT_EXIST);
        }

        response.setMimeType("application/json");


        //Allow CORS
        response.addHeader("Access-Control-Allow-Origin","*");
        response.addHeader("Access-Control-Allow-Headers","auth-user, auth-password");
        return response;
    }

    public String getCommand(String uri)
    {
        String[] uriSection = uri.split("/");
        if (uriSection.length > 1) {
            return "/" + uriSection[1];
        }
        return "/null";
    }

    public abstract Song playOrPauseCommand();

    public abstract Song playCommand();

    public abstract Song pauseCommand();

    public abstract Song stopCommand();

    public abstract Song previousCommand();

    public abstract Song nextCommand();

    public abstract Boolean loopCommand();

    public abstract Boolean shuffleCommand();

    public abstract void seekToCommand(int mSec);

    public abstract Song getCurrentSong();
}

