/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.arquillian.example.arquilliantutorial;

import java.io.PrintStream;
import javax.inject.Inject;

/**
 * A component for creating personal greetings.
 */
public class Greeter
{

    private final PhraseBuilder phraseBuilder;

    @Inject
    public Greeter(PhraseBuilder phraseBuilder)
    {
        this.phraseBuilder = phraseBuilder;
    }

    public void greet(PrintStream to, String name)
    {
        to.println(createGreeting(name));
    }

    public String createGreeting(String name)
    {
        return phraseBuilder.buildPhrase("hello", name);
    }
}
