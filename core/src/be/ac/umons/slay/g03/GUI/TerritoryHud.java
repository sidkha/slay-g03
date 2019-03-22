package be.ac.umons.slay.g03.GUI;

import be.ac.umons.slay.g03.Core.Territory;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

class TerritoryHud extends Stage {

    HudButton soldier0, soldier1, soldier2, soldier3, defenceTower, attackTower, boat, mine;
    HudButton buttons[];
    TextButton nextTurn;
    ShapeRenderer shapeRenderer;
    SpriteBatch batch;
    World world;
    private int w = ScreenHandler.WIDTH;
    private int h = ScreenHandler.HEIGHT;

    TerritoryHud(World world) {
        this.world = world;
        initActors();
    }

    private void initActors() {
        soldier0 = new HudButton("          10", ScreenHandler.game.skin, 10);
        soldier1 = new HudButton("          20", ScreenHandler.game.skin, 20);
        soldier2 = new HudButton("          40", ScreenHandler.game.skin, 40);
        soldier3 = new HudButton("          80", ScreenHandler.game.skin, 80);
        attackTower = new HudButton("          25", ScreenHandler.game.skin, 25);
        defenceTower = new HudButton("          10", ScreenHandler.game.skin, 10);
        boat = new HudButton("          25", ScreenHandler.game.skin, 25);
        mine = new HudButton("          20", ScreenHandler.game.skin, 20);

        this.buttons = new HudButton[]{soldier0, soldier1, soldier2, soldier3, defenceTower, attackTower, boat, mine};

        shapeRenderer = new ShapeRenderer();
        batch = new SpriteBatch();
        int i = 0;
        for (HudButton button : buttons) {
            button.setPosition((float) w / 24 + i, (float) h / 100);
            button.setHeight(38f);
            i += 96;
            this.addActor(button);
        }

        soldier0.addListener(createClickListener("soldier0"));
        soldier1.addListener(createClickListener("soldier1"));
        soldier2.addListener(createClickListener("soldier2"));
        soldier3.addListener(createClickListener("soldier3"));
        defenceTower.addListener(createClickListener("defenceTower"));
        attackTower.addListener(createClickListener("attackTower"));
        boat.addListener(createClickListener("boat"));
        mine.addListener(createClickListener("mine"));
    }

    @Override
    public void draw() {
        Territory territory = world.gameState.getStates().getTerritory();
        TextureAtlas.AtlasRegion[] buttonImages = {world.soldier0, world.soldier1, world.soldier2, world.soldier3, world.defenceTower, world.attackTower, world.boat, world.mine};

        if (territory != null)
            checkPrice(buttons, territory);
        else if (!world.gameState.getStates().isSelectionMode()) {
            for (HudButton button : buttons)
                button.setColor(Color.GRAY);
        }
        if (world.gameState.getStates().isSelectionMode() || world.gameState.getStates().isTerritorySelected()) {
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(Color.LIGHT_GRAY);
            shapeRenderer.rect(0, 0, w, 48);
            shapeRenderer.end();

            super.draw();
            batch.begin();
            int i = 0;
            for (TextureAtlas.AtlasRegion image : buttonImages) {
                batch.draw(new TextureRegion(image), (float) (w / 22) + i, (float) (h / 65));
                i += 96;
            }
            batch.end();
        }
        if (territory != null) {
            if ((world.map.getPlayer1().isTurn() && territory.findCapital().getOwner() == world.map.getPlayer1()) ||
                    (world.map.getPlayer2().isTurn() && territory.findCapital().getOwner() == world.map.getPlayer2())) {
                batch.begin();
                showTerritoryInfo(batch, territory);
                batch.end();
            }
        }
    }

    private void checkPrice(HudButton[] buttons, Territory territory) {
        for (HudButton button : buttons) {
            if (territory.findCapital().getMoney() < button.getCost()) {
                button.setColor(Color.RED);
            }
        }
    }

    private ClickListener createClickListener(String name) {
        return new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (world.gameState.getStates().isTerritorySelected())
                    world.gameState.getStates().setCreationMode(!world.gameState.getStates().isCreationMode());
                world.gameState.setElementToBuild(name);
            }
        };
    }

    private void showTerritoryInfo(SpriteBatch batch, Territory territory) {
        String money = "Money: " + territory.findCapital().getMoney();
        String gain = "Gain: " + territory.getGainThisTurn();
        ScreenHandler.game.font.draw(batch, money + " " + gain, 0, h);
    }

}
