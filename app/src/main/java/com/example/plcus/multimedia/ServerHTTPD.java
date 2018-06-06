package com.example.plcus.multimedia;

import fi.iki.elonen.NanoHTTPD;

// Ref. : https://stackoverflow.com/questions/14309256/using-nanohttpd-in-android
public abstract class ServerHTTPD extends NanoHTTPD {

    private Playlist playlist;

    public ServerHTTPD(Playlist playlist)
    {
        super(8080);
        this.playlist = playlist;
    }

    @Override
    public Response serve(IHTTPSession session) {

        Response response;
        if (session.getMethod() == Method.GET) {
            String uri = session.getUri();
            String command = getCommand(uri);

            switch (getCommand(uri)) {
                case ServerCommand.PLAY_OR_PAUSE:
                    playOrPauseCommand();
                    response = newFixedLengthResponse(ServerCommand.PLAY_OR_PAUSE);
                    break;
                case ServerCommand.PLAY:
                    playCommand();
                    response = newFixedLengthResponse(ServerCommand.PLAY);
                    break;
                case ServerCommand.PAUSE:
                    pauseCommand();
                    response = newFixedLengthResponse(ServerCommand.PAUSE);
                    break;
                case ServerCommand.STOP:
                    stopCommand();
                    response = newFixedLengthResponse(ServerCommand.STOP);
                    break;
                case ServerCommand.PREVIOUS:
                    previousCommand();
                    response = newFixedLengthResponse(ServerCommand.PREVIOUS);
                    break;
                case ServerCommand.NEXT:
                    nextCommand();
                    response = newFixedLengthResponse(ServerCommand.NEXT);
                    break;
                case ServerCommand.LOOP:
                    loopCommand();
                    response = newFixedLengthResponse(ServerCommand.LOOP);
                    break;
                case ServerCommand.SHUFFLE:
                    shuffleCommand();
                    response = newFixedLengthResponse(ServerCommand.SHUFFLE);
                    break;
                case ServerCommand.SEEK:
                    seekToCommand(5000);
                    response = newFixedLengthResponse(ServerCommand.SEEK);
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

    public abstract void playOrPauseCommand();

    public abstract void playCommand();

    public abstract void pauseCommand();

    public abstract void stopCommand();

    public abstract void previousCommand();

    public abstract void nextCommand();

    public abstract void loopCommand();

    public abstract void shuffleCommand();

    public abstract void seekToCommand(int mSec);
}

