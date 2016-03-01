package com.mygdx.livex;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Radek on 23.02.2016.
 */
public class Question {
    Answer answerA;
    Answer answerB;
    Answer answerC;
    AnimatablePositionedTexture question;
    List<Answer> answers = new ArrayList<Answer>(3);

    public Question(String ansA, String ansB, String ansC, String question, boolean a1, boolean a2, boolean a3) {

        answerA = new Answer(576, 202, new Texture(ansA), a1);
        answerB = new Answer(522, 452, new Texture(ansB), a2);
        answerC = new Answer(839, 340, new Texture(ansC), a3);

        answers.add(answerA);
        answers.add(answerB);
        answers.add(answerC);

        this.question = new AnimatablePositionedTexture(question, 633, 65);
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void startAnimationIn() {
        answerA.startAnimationIn();
        answerB.startAnimationIn();
        answerC.startAnimationIn();
        question.startAnimationIn();
    }

    public void startAnimationOut() {
        answerA.startAnimationOut();
        answerB.startAnimationOut();
        answerC.startAnimationOut();
        question.startAnimationOut();
    }

    public void reset() {
        answerA.reset();
        answerB.reset();
        answerC.reset();
        question.reset();
    }


    public AnimatablePositionedTexture getQuestion() {
        return question;
    }

    public boolean isAnimating() {
        return question.isAnimating;
    }

    public void animate(float deltaTime) {
        answerA.animate(deltaTime);
        answerB.animate(deltaTime);
        answerC.animate(deltaTime);
        question.animate(deltaTime);
    }

    public void draw(SpriteBatch batch) {
        answerA.draw(batch);
        answerB.draw(batch);
        answerC.draw(batch);
        question.draw(batch);
    }
}
