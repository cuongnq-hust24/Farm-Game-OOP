package com.cousersoft.game.graphics;

public class Sprite {
	
	public final int SIZE; //Size for the individual sprite
	private final static int TSIZE = 16; //Size of tiles in the game
	private int x; //x location of the sprite
	private int y; //y location of the sprite
	public int[] pixels; //array to hold each pixel in the sprite
	private int width, height;
	protected SpriteSheet sheet; //What spritesheet hold the sprite
	
	//Declare sprites here
	//ALL GRASS TILES///////////////////////////////////////////////////////////////////////////////////////
	public static Sprite grass = new Sprite(TSIZE, 0, 0, SpriteSheet.tiles);
	public static Sprite grass1 = new Sprite(TSIZE,1, 0, SpriteSheet.tiles);
	public static Sprite grass2 = new Sprite(TSIZE, 0, 1, SpriteSheet.tiles);
	public static Sprite grass3 = new Sprite(TSIZE, 1, 1, SpriteSheet.tiles);
	public static Sprite yellowFlower = new Sprite(TSIZE, 2, 0, SpriteSheet.tiles);
	public static Sprite bigFlower = new Sprite(TSIZE, 3, 0, SpriteSheet.tiles);
	public static Sprite purpleFlower = new Sprite(TSIZE, 2, 1, SpriteSheet.tiles);
	public static Sprite rock = new Sprite(TSIZE, 10, 0, SpriteSheet.tiles);
	//ALL DIRT TILES///////////////////////////////////////////////////////////////////////////////////////////
	public static Sprite dirt = new Sprite(TSIZE, 3, 1, SpriteSheet.tiles);
	public static Sprite dirtUp1 = new Sprite(TSIZE, 4, 1, SpriteSheet.tiles);
	public static Sprite dirtUp2 = new Sprite(TSIZE, 4, 0, SpriteSheet.tiles);
	public static Sprite dirtDown1 = new Sprite(TSIZE, 5, 1, SpriteSheet.tiles);
	public static Sprite dirtDown2 = new Sprite(TSIZE, 5, 0, SpriteSheet.tiles);
	public static Sprite dirtRight = new Sprite(TSIZE, 6, 0, SpriteSheet.tiles);
	public static Sprite dirtBottom = new Sprite(TSIZE, 6, 1, SpriteSheet.tiles);
	public static Sprite dirtLeft = new Sprite(TSIZE, 7, 0, SpriteSheet.tiles);
	public static Sprite dirtTop = new Sprite(TSIZE, 7, 1, SpriteSheet.tiles);
	public static Sprite dirtTLCorner = new Sprite(TSIZE, 8, 0, SpriteSheet.tiles);
	public static Sprite dirtTRCorner = new Sprite(TSIZE, 9, 0, SpriteSheet.tiles);
	public static Sprite dirtBLCorner = new Sprite(TSIZE, 8, 1, SpriteSheet.tiles);
	public static Sprite dirtBRCorner = new Sprite(TSIZE, 9, 1, SpriteSheet.tiles);
	public static Sprite soil = new Sprite(TSIZE, 11, 0, SpriteSheet.tiles);
	public static Sprite soilTilled = new Sprite(TSIZE, 12, 0, SpriteSheet.tiles);
	public static Sprite soilWatered = new Sprite(TSIZE, 13, 0, SpriteSheet.tiles);
	//TREE TILES////////////////////////////////////////////////////////////////////////////////////////////////
	public static Sprite tlLeave = new Sprite(TSIZE, 1, 2, SpriteSheet.tiles);
	public static Sprite tmLeave = new Sprite(TSIZE, 2, 2, SpriteSheet.tiles);
	public static Sprite trLeave = new Sprite(TSIZE, 3, 2, SpriteSheet.tiles);
	public static Sprite mlLeave = new Sprite(TSIZE, 0, 3, SpriteSheet.tiles);
	public static Sprite mmlLeave = new Sprite(TSIZE, 1, 3, SpriteSheet.tiles);
	public static Sprite mmLeave = new Sprite(TSIZE, 2, 3, SpriteSheet.tiles);
	public static Sprite mmrLeave = new Sprite(TSIZE, 3, 3, SpriteSheet.tiles);
	public static Sprite mrLeave = new Sprite(TSIZE, 4, 3, SpriteSheet.tiles);
	public static Sprite blLeave = new Sprite(TSIZE, 0, 4, SpriteSheet.tiles);
	public static Sprite blmLeave = new Sprite(TSIZE, 1, 4, SpriteSheet.tiles);
	public static Sprite bmLeave = new Sprite(TSIZE, 2, 4, SpriteSheet.tiles);
	public static Sprite brmLeave = new Sprite(TSIZE, 3, 4, SpriteSheet.tiles);
	public static Sprite brLeave = new Sprite(TSIZE, 4, 4, SpriteSheet.tiles);
	public static Sprite mlTrunk = new Sprite(TSIZE, 0, 5, SpriteSheet.tiles);
	public static Sprite mmlTrunk = new Sprite(TSIZE, 1, 5, SpriteSheet.tiles);
	public static Sprite mmTrunk = new Sprite(TSIZE, 2, 5, SpriteSheet.tiles);
	public static Sprite mmrTrunk = new Sprite(TSIZE,3, 5, SpriteSheet.tiles);
	public static Sprite mrTrunk = new Sprite(TSIZE, 4, 5, SpriteSheet.tiles);
	public static Sprite blTrunk = new Sprite(TSIZE, 1, 6, SpriteSheet.tiles);
	public static Sprite Trunk = new Sprite(TSIZE, 2, 6, SpriteSheet.tiles);
	public static Sprite brTrunk = new Sprite(TSIZE, 3, 6, SpriteSheet.tiles);

	// NEW TEST TILES
	// Row 0: Grass variants
	public static Sprite nGrass1 = new Sprite(TSIZE, 0, 0, SpriteSheet.test_tiles);
	public static Sprite nGrass2 = new Sprite(TSIZE, 1, 0, SpriteSheet.test_tiles);
	public static Sprite nGrass3 = new Sprite(TSIZE, 2, 0, SpriteSheet.test_tiles);
	public static Sprite nGrass4 = new Sprite(TSIZE, 3, 0, SpriteSheet.test_tiles);
	public static Sprite nGrass5 = new Sprite(TSIZE, 4, 0, SpriteSheet.test_tiles);
	public static Sprite nGrassDry1 = new Sprite(TSIZE, 5, 0, SpriteSheet.test_tiles);
	public static Sprite nGrassDry2 = new Sprite(TSIZE, 6, 0, SpriteSheet.test_tiles);
	public static Sprite nGrassDry3 = new Sprite(TSIZE, 7, 0, SpriteSheet.test_tiles);
	public static Sprite nGrassWinter1 = new Sprite(TSIZE, 8, 0, SpriteSheet.test_tiles);
	public static Sprite nGrassWinter2 = new Sprite(TSIZE, 9, 0, SpriteSheet.test_tiles);
	public static Sprite nGrassWinter3 = new Sprite(TSIZE, 10, 0, SpriteSheet.test_tiles);
	public static Sprite nGrassWinter4 = new Sprite(TSIZE, 11, 0, SpriteSheet.test_tiles);
	public static Sprite nGrassWinter5 = new Sprite(TSIZE, 12, 0, SpriteSheet.test_tiles);

	// Row 1: Farmland Top
	public static Sprite fTopLeft = new Sprite(TSIZE, 0, 1, SpriteSheet.test_tiles);
	public static Sprite fTop = new Sprite(TSIZE, 1, 1, SpriteSheet.test_tiles);
	public static Sprite fTopRight = new Sprite(TSIZE, 2, 1, SpriteSheet.test_tiles);
	public static Sprite fTopLeftWet1 = new Sprite(TSIZE, 3, 1, SpriteSheet.test_tiles);
	public static Sprite fTopWet1 = new Sprite(TSIZE, 4, 1, SpriteSheet.test_tiles);
	public static Sprite fTopRightWet1 = new Sprite(TSIZE, 5, 1, SpriteSheet.test_tiles);
	public static Sprite fTopLeftWet2 = new Sprite(TSIZE, 6, 1, SpriteSheet.test_tiles);
	public static Sprite fTopWet2 = new Sprite(TSIZE, 7, 1, SpriteSheet.test_tiles);
	public static Sprite fTopRightWet2 = new Sprite(TSIZE, 8, 1, SpriteSheet.test_tiles);

	// Row 2: Farmland Middle
	public static Sprite fLeft = new Sprite(TSIZE, 0, 2, SpriteSheet.test_tiles);
	public static Sprite fCenter = new Sprite(TSIZE, 1, 2, SpriteSheet.test_tiles);
	public static Sprite fRight = new Sprite(TSIZE, 2, 2, SpriteSheet.test_tiles);
	public static Sprite fLeftWet1 = new Sprite(TSIZE, 3, 2, SpriteSheet.test_tiles);
	public static Sprite fCenterWet1 = new Sprite(TSIZE, 4, 2, SpriteSheet.test_tiles);
	public static Sprite fRightWet1 = new Sprite(TSIZE, 5, 2, SpriteSheet.test_tiles);
	public static Sprite fLeftWet2 = new Sprite(TSIZE, 6, 2, SpriteSheet.test_tiles);
	public static Sprite fCenterWet2 = new Sprite(TSIZE, 7, 2, SpriteSheet.test_tiles);
	public static Sprite fRightWet2 = new Sprite(TSIZE, 8, 2, SpriteSheet.test_tiles);

	// Row 3: Farmland Bottom
	public static Sprite fBotLeft = new Sprite(TSIZE, 0, 3, SpriteSheet.test_tiles);
	public static Sprite fBot = new Sprite(TSIZE, 1, 3, SpriteSheet.test_tiles);
	public static Sprite fBotRight = new Sprite(TSIZE, 2, 3, SpriteSheet.test_tiles);
	public static Sprite fBotLeftWet1 = new Sprite(TSIZE, 3, 3, SpriteSheet.test_tiles);
	public static Sprite fBotWet1 = new Sprite(TSIZE, 4, 3, SpriteSheet.test_tiles);
	public static Sprite fBotRightWet1 = new Sprite(TSIZE, 5, 3, SpriteSheet.test_tiles);
	public static Sprite fBotLeftWet2 = new Sprite(TSIZE, 6, 3, SpriteSheet.test_tiles);
	public static Sprite fBotWet2 = new Sprite(TSIZE, 7, 3, SpriteSheet.test_tiles);
	public static Sprite fBotRightWet2 = new Sprite(TSIZE, 8, 3, SpriteSheet.test_tiles);

	// SNOW FARMLAND (Rows 1-3, Col 9-11)
	public static Sprite fSnowTopLeft = new Sprite(TSIZE, 9, 1, SpriteSheet.test_tiles);
	public static Sprite fSnowTop = new Sprite(TSIZE, 10, 1, SpriteSheet.test_tiles);
	public static Sprite fSnowTopRight = new Sprite(TSIZE, 11, 1, SpriteSheet.test_tiles);
	public static Sprite fSnowLeft = new Sprite(TSIZE, 9, 2, SpriteSheet.test_tiles);
	public static Sprite fSnowCenter = new Sprite(TSIZE, 10, 2, SpriteSheet.test_tiles);
	public static Sprite fSnowRight = new Sprite(TSIZE, 11, 2, SpriteSheet.test_tiles);
	public static Sprite fSnowBotLeft = new Sprite(TSIZE, 9, 3, SpriteSheet.test_tiles);
	public static Sprite fSnowBot = new Sprite(TSIZE, 10, 3, SpriteSheet.test_tiles);
	public static Sprite fSnowBotRight = new Sprite(TSIZE, 11, 3, SpriteSheet.test_tiles);

	// HEATWAVE DRY-FARMLAND (Rows 4-6, Col 0-8)
	// Row 4: Top
	public static Sprite fHeatTopLeft = new Sprite(TSIZE, 0, 4, SpriteSheet.test_tiles);
	public static Sprite fHeatTop = new Sprite(TSIZE, 1, 4, SpriteSheet.test_tiles);
	public static Sprite fHeatTopRight = new Sprite(TSIZE, 2, 4, SpriteSheet.test_tiles);
	public static Sprite fHeatTopLeftWet1 = new Sprite(TSIZE, 3, 4, SpriteSheet.test_tiles);
	public static Sprite fHeatTopWet1 = new Sprite(TSIZE, 4, 4, SpriteSheet.test_tiles);
	public static Sprite fHeatTopRightWet1 = new Sprite(TSIZE, 5, 4, SpriteSheet.test_tiles);
	public static Sprite fHeatTopLeftWet2 = new Sprite(TSIZE, 6, 4, SpriteSheet.test_tiles);
	public static Sprite fHeatTopWet2 = new Sprite(TSIZE, 7, 4, SpriteSheet.test_tiles);
	public static Sprite fHeatTopRightWet2 = new Sprite(TSIZE, 8, 4, SpriteSheet.test_tiles);
	// Row 5: Middle
	public static Sprite fHeatLeft = new Sprite(TSIZE, 0, 5, SpriteSheet.test_tiles);
	public static Sprite fHeatCenter = new Sprite(TSIZE, 1, 5, SpriteSheet.test_tiles);
	public static Sprite fHeatRight = new Sprite(TSIZE, 2, 5, SpriteSheet.test_tiles);
	public static Sprite fHeatLeftWet1 = new Sprite(TSIZE, 3, 5, SpriteSheet.test_tiles);
	public static Sprite fHeatCenterWet1 = new Sprite(TSIZE, 4, 5, SpriteSheet.test_tiles);
	public static Sprite fHeatRightWet1 = new Sprite(TSIZE, 5, 5, SpriteSheet.test_tiles);
	public static Sprite fHeatLeftWet2 = new Sprite(TSIZE, 6, 5, SpriteSheet.test_tiles);
	public static Sprite fHeatCenterWet2 = new Sprite(TSIZE, 7, 5, SpriteSheet.test_tiles);
	public static Sprite fHeatRightWet2 = new Sprite(TSIZE, 8, 5, SpriteSheet.test_tiles);
	// Row 6: Bottom
	public static Sprite fHeatBotLeft = new Sprite(TSIZE, 0, 6, SpriteSheet.test_tiles);
	public static Sprite fHeatBot = new Sprite(TSIZE, 1, 6, SpriteSheet.test_tiles);
	public static Sprite fHeatBotRight = new Sprite(TSIZE, 2, 6, SpriteSheet.test_tiles);
	public static Sprite fHeatBotLeftWet1 = new Sprite(TSIZE, 3, 6, SpriteSheet.test_tiles);
	public static Sprite fHeatBotWet1 = new Sprite(TSIZE, 4, 6, SpriteSheet.test_tiles);
	public static Sprite fHeatBotRightWet1 = new Sprite(TSIZE, 5, 6, SpriteSheet.test_tiles);
	public static Sprite fHeatBotLeftWet2 = new Sprite(TSIZE, 6, 6, SpriteSheet.test_tiles);
	public static Sprite fHeatBotWet2 = new Sprite(TSIZE, 7, 6, SpriteSheet.test_tiles);
	public static Sprite fHeatBotRightWet2 = new Sprite(TSIZE, 8, 6, SpriteSheet.test_tiles);
	//VOID TILE/////////////////////////////////////////////////////////////
	public static Sprite voidSprite = new Sprite(TSIZE, 0);
	public static Sprite clearSprite = new Sprite(TSIZE, 0x21000000);
	
	//PLAYER SPRITES///////////////////////////////////////////////////////////
	public static Sprite player_down = new Sprite(16, 1, 0, SpriteSheet.player);
		public static Sprite player_down_1 = new Sprite(16, 0, 0, SpriteSheet.player);
		public static Sprite player_down_2 = new Sprite(16, 2, 0, SpriteSheet.player);
	public static Sprite player_up = new Sprite(16, 1, 1, SpriteSheet.player);
		public static Sprite player_up_1 = new Sprite(16, 0, 1, SpriteSheet.player);
		public static Sprite player_up_2 = new Sprite(16, 2, 1, SpriteSheet.player);
	public static Sprite player_left = new Sprite(16, 1, 3, SpriteSheet.player);
		public static Sprite player_left_1 = new Sprite(16, 0, 3, SpriteSheet.player);
		public static Sprite player_left_2 = new Sprite(16, 2, 3, SpriteSheet.player);
	public static Sprite player_right = new Sprite(16, 1, 2, SpriteSheet.player);
		public static Sprite player_right_1 = new Sprite(16, 0, 2, SpriteSheet.player);
		public static Sprite player_right_2 = new Sprite(16, 2, 2, SpriteSheet.player);
	public static Sprite playerRight1 = new Sprite(16, 3, 0, SpriteSheet.player);
	public static Sprite playerRight2 = new Sprite(16, 3, 1, SpriteSheet.player);
	public static Sprite playerLeft1 = new Sprite(16,3,2,SpriteSheet.player);
	public static Sprite playerLeft2 = new Sprite(16,3,3,SpriteSheet.player);
	public static Sprite playerDown1 = new Sprite(16,4,0,SpriteSheet.player);
	public static Sprite playerDown2 = new Sprite(16,4,1,SpriteSheet.player);
	//NPC SPRITES////////////////////////////////////////////////////////////////////////////
	public static Sprite npc_down = new Sprite(16, 1, 0, SpriteSheet.NPC);	
		public static Sprite npc_down_1 = new Sprite(16, 0, 0, SpriteSheet.NPC);
		public static Sprite npc_down_2 = new Sprite(16, 2, 0, SpriteSheet.NPC);
	public static Sprite npc_up = new Sprite(16, 1, 1, SpriteSheet.NPC);
		public static Sprite npc_up_1 = new Sprite(16, 0, 1, SpriteSheet.NPC);
		public static Sprite npc_up_2 = new Sprite(16, 2, 1, SpriteSheet.NPC);
	public static Sprite npc_left = new Sprite(16, 1, 3, SpriteSheet.NPC);
		public static Sprite npc_left_1 = new Sprite(16, 0, 3, SpriteSheet.NPC);
		public static Sprite npc_left_2 = new Sprite(16, 2, 3, SpriteSheet.NPC);
	public static Sprite npc_right = new Sprite(16, 1, 2, SpriteSheet.NPC);
		public static Sprite npc_right_1 = new Sprite(16, 0, 2, SpriteSheet.NPC);
		public static Sprite npc_right_2 = new Sprite(16, 2, 2, SpriteSheet.NPC);
	
	public static Sprite sword = new Sprite(16, 0, 0, SpriteSheet.sword);
	public static Sprite ladybug = new Sprite(16, 0, 0, SpriteSheet.ladybug);
	
	////////////FONT SPRITES///////////////////////////////////////////////////////////////////////////////////////
	public static Sprite capA = new Sprite(16, 0, 0, SpriteSheet.fontSheet);
	public static Sprite capB = new Sprite(16, 1, 0, SpriteSheet.fontSheet);
	public static Sprite capC = new Sprite(16, 2, 0, SpriteSheet.fontSheet);
	public static Sprite capD = new Sprite(16, 3, 0, SpriteSheet.fontSheet);
	public static Sprite capE = new Sprite(16, 4, 0, SpriteSheet.fontSheet);
	public static Sprite capF = new Sprite(16, 5, 0, SpriteSheet.fontSheet);
	public static Sprite capG = new Sprite(16, 6, 0, SpriteSheet.fontSheet);
	public static Sprite capH = new Sprite(16, 7, 0, SpriteSheet.fontSheet);
	public static Sprite capI = new Sprite(16, 8, 0, SpriteSheet.fontSheet);
	public static Sprite capJ = new Sprite(16, 9, 0, SpriteSheet.fontSheet);
	public static Sprite capK = new Sprite(16, 10, 0, SpriteSheet.fontSheet);
	public static Sprite capL = new Sprite(16, 11, 0, SpriteSheet.fontSheet);
	public static Sprite capM = new Sprite(16, 12, 0, SpriteSheet.fontSheet);
	public static Sprite capN = new Sprite(16, 13, 0, SpriteSheet.fontSheet);
	public static Sprite capO = new Sprite(16, 14, 0, SpriteSheet.fontSheet);
	public static Sprite capP = new Sprite(16, 0, 1, SpriteSheet.fontSheet);
	public static Sprite capQ = new Sprite(16, 1, 1, SpriteSheet.fontSheet);
	public static Sprite capR = new Sprite(16, 2, 1, SpriteSheet.fontSheet);
	public static Sprite capS = new Sprite(16, 3, 1, SpriteSheet.fontSheet);
	public static Sprite capT = new Sprite(16, 4, 1, SpriteSheet.fontSheet);
	public static Sprite capU = new Sprite(16, 5, 1, SpriteSheet.fontSheet);
	public static Sprite capV = new Sprite(16, 6, 1, SpriteSheet.fontSheet);
	public static Sprite capW = new Sprite(16, 7, 1, SpriteSheet.fontSheet);
	public static Sprite capX = new Sprite(16, 8, 1, SpriteSheet.fontSheet);
	public static Sprite capY = new Sprite(16, 9, 1, SpriteSheet.fontSheet);
	public static Sprite capZ = new Sprite(16, 10, 1, SpriteSheet.fontSheet);
	///LOWER CASE LETTERS////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static Sprite lowA = new Sprite(16, 11, 1, SpriteSheet.fontSheet);
	public static Sprite lowB = new Sprite(16, 12, 1, SpriteSheet.fontSheet);
	public static Sprite lowC = new Sprite(16, 13, 1, SpriteSheet.fontSheet);
	public static Sprite lowD = new Sprite(16, 14, 1, SpriteSheet.fontSheet);
	public static Sprite lowE = new Sprite(16, 0, 2, SpriteSheet.fontSheet);
	public static Sprite lowF = new Sprite(16, 1, 2, SpriteSheet.fontSheet);
	public static Sprite lowG = new Sprite(16, 2, 2, SpriteSheet.fontSheet);
	public static Sprite lowH = new Sprite(16, 3, 2, SpriteSheet.fontSheet);
	public static Sprite lowI = new Sprite(16, 4, 2, SpriteSheet.fontSheet);
	public static Sprite lowJ = new Sprite(16, 5, 2, SpriteSheet.fontSheet);
	public static Sprite lowK = new Sprite(16, 6, 2, SpriteSheet.fontSheet);
	public static Sprite lowL = new Sprite(16, 7, 2, SpriteSheet.fontSheet);
	public static Sprite lowM = new Sprite(16, 8, 2, SpriteSheet.fontSheet);
	public static Sprite lowN = new Sprite(16, 9, 2, SpriteSheet.fontSheet);
	public static Sprite lowO = new Sprite(16, 10, 2, SpriteSheet.fontSheet);
	public static Sprite lowP = new Sprite(16, 11, 2, SpriteSheet.fontSheet);
	public static Sprite lowQ = new Sprite(16, 12, 2, SpriteSheet.fontSheet);
	public static Sprite lowR = new Sprite(16, 13, 2, SpriteSheet.fontSheet);
	public static Sprite lowS = new Sprite(16, 14, 2, SpriteSheet.fontSheet);
	public static Sprite lowT = new Sprite(16, 0, 3, SpriteSheet.fontSheet);
	public static Sprite lowU = new Sprite(16, 1, 3, SpriteSheet.fontSheet);
	public static Sprite lowV = new Sprite(16, 2, 3, SpriteSheet.fontSheet);
	public static Sprite lowW = new Sprite(16, 3, 3, SpriteSheet.fontSheet);
	public static Sprite lowX = new Sprite(16, 4, 3, SpriteSheet.fontSheet);
	public static Sprite lowY = new Sprite(16, 5, 3, SpriteSheet.fontSheet);
	public static Sprite lowZ = new Sprite(16, 6, 3, SpriteSheet.fontSheet);
	public static Sprite period = new Sprite(16, 7, 3, SpriteSheet.fontSheet);
	public static Sprite comma = new Sprite(16, 8, 3, SpriteSheet.fontSheet);
	public static Sprite expPt = new Sprite(16, 9, 3, SpriteSheet.fontSheet);
	public static Sprite question = new Sprite(16, 10, 3, SpriteSheet.fontSheet);
	/////////////////////////////SMALL FONT SPRITES///////////////////////////////////////////////////////////////////////////////////
	public static Sprite smcapA = new Sprite(8, 0, 0, SpriteSheet.smallFontSheet);
	public static Sprite smcapB = new Sprite(8, 1, 0, SpriteSheet.smallFontSheet);
	public static Sprite smcapC = new Sprite(8, 2, 0, SpriteSheet.smallFontSheet);
	public static Sprite smcapD = new Sprite(8, 3, 0, SpriteSheet.smallFontSheet);
	public static Sprite smcapE = new Sprite(8, 4, 0, SpriteSheet.smallFontSheet);
	public static Sprite smcapF = new Sprite(8, 5, 0, SpriteSheet.smallFontSheet);
	public static Sprite smcapG = new Sprite(8, 6, 0, SpriteSheet.smallFontSheet);
	public static Sprite smcapH = new Sprite(8, 7, 0, SpriteSheet.smallFontSheet);
	public static Sprite smcapI = new Sprite(8, 8, 0, SpriteSheet.smallFontSheet);
	public static Sprite smcapJ = new Sprite(8, 9, 0, SpriteSheet.smallFontSheet);
	public static Sprite smcapK = new Sprite(8, 10, 0, SpriteSheet.smallFontSheet);
	public static Sprite smcapL = new Sprite(8, 11, 0, SpriteSheet.smallFontSheet);
	public static Sprite smcapM = new Sprite(8, 12, 0, SpriteSheet.smallFontSheet);
	public static Sprite smcapN = new Sprite(8, 0, 1, SpriteSheet.smallFontSheet);
	public static Sprite smcapO = new Sprite(8, 1, 1, SpriteSheet.smallFontSheet);
	public static Sprite smcapP = new Sprite(8, 2, 1, SpriteSheet.smallFontSheet);
	public static Sprite smcapQ = new Sprite(8, 3, 1, SpriteSheet.smallFontSheet);
	public static Sprite smcapR = new Sprite(8, 4, 1, SpriteSheet.smallFontSheet);
	public static Sprite smcapS = new Sprite(8, 5, 1, SpriteSheet.smallFontSheet);
	public static Sprite smcapT = new Sprite(8, 6, 1, SpriteSheet.smallFontSheet);
	public static Sprite smcapU = new Sprite(8, 7, 1, SpriteSheet.smallFontSheet);
	public static Sprite smcapV = new Sprite(8, 8, 1, SpriteSheet.smallFontSheet);
	public static Sprite smcapW = new Sprite(8, 9, 1, SpriteSheet.smallFontSheet);
	public static Sprite smcapX = new Sprite(8, 10, 1, SpriteSheet.smallFontSheet);
	public static Sprite smcapY = new Sprite(8, 11, 1, SpriteSheet.smallFontSheet);
	public static Sprite smcapZ = new Sprite(8, 12, 1, SpriteSheet.smallFontSheet);
	///LOWER CASE LETTERS////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static Sprite smlowA = new Sprite(8, 0, 2, SpriteSheet.smallFontSheet);
	public static Sprite smlowB = new Sprite(8, 1, 2, SpriteSheet.smallFontSheet);
	public static Sprite smlowC = new Sprite(8, 2, 2, SpriteSheet.smallFontSheet);
	public static Sprite smlowD = new Sprite(8, 3, 2, SpriteSheet.smallFontSheet);
	public static Sprite smlowE = new Sprite(8, 4, 2, SpriteSheet.smallFontSheet);
	public static Sprite smlowF = new Sprite(8, 5, 2, SpriteSheet.smallFontSheet);
	public static Sprite smlowG = new Sprite(8, 6, 2, SpriteSheet.smallFontSheet);
	public static Sprite smlowH = new Sprite(8, 7, 2, SpriteSheet.smallFontSheet);
	public static Sprite smlowI = new Sprite(8, 8, 2, SpriteSheet.smallFontSheet);
	public static Sprite smlowJ = new Sprite(8, 9, 2, SpriteSheet.smallFontSheet);
	public static Sprite smlowK = new Sprite(8, 10, 2, SpriteSheet.smallFontSheet);
	public static Sprite smlowL = new Sprite(8, 11, 2, SpriteSheet.smallFontSheet);
	public static Sprite smlowM = new Sprite(8, 12, 2, SpriteSheet.smallFontSheet);
	public static Sprite smlowN = new Sprite(8, 0, 3, SpriteSheet.smallFontSheet);
	public static Sprite smlowO = new Sprite(8, 1, 3, SpriteSheet.smallFontSheet);
	public static Sprite smlowP = new Sprite(8, 2, 3, SpriteSheet.smallFontSheet);
	public static Sprite smlowQ = new Sprite(8, 3, 3, SpriteSheet.smallFontSheet);
	public static Sprite smlowR = new Sprite(8, 4, 3, SpriteSheet.smallFontSheet);
	public static Sprite smlowS = new Sprite(8, 5, 3, SpriteSheet.smallFontSheet);
	public static Sprite smlowT = new Sprite(8, 6, 3, SpriteSheet.smallFontSheet);
	public static Sprite smlowU = new Sprite(8, 7, 3, SpriteSheet.smallFontSheet);
	public static Sprite smlowV = new Sprite(8, 8, 3, SpriteSheet.smallFontSheet);
	public static Sprite smlowW = new Sprite(8, 9, 3, SpriteSheet.smallFontSheet);
	public static Sprite smlowX = new Sprite(8, 10, 3, SpriteSheet.smallFontSheet);
	public static Sprite smlowY = new Sprite(8, 11, 3, SpriteSheet.smallFontSheet);
	public static Sprite smlowZ = new Sprite(8, 12, 3, SpriteSheet.smallFontSheet);
	public static Sprite smPeriod = new Sprite(8, 0, 4, SpriteSheet.smallFontSheet);
	public static Sprite smComma = new Sprite(8, 1, 4, SpriteSheet.smallFontSheet);
	public static Sprite smExplPt = new Sprite(8, 2, 4, SpriteSheet.smallFontSheet);
	public static Sprite smQuestion = new Sprite(8, 3, 4, SpriteSheet.smallFontSheet);
	public static Sprite smColon = new Sprite(8, 4, 4, SpriteSheet.smallFontSheet);
	public static Sprite sm1 = new Sprite(8, 5, 4, SpriteSheet.smallFontSheet);
	public static Sprite sm2 = new Sprite(8, 6, 4, SpriteSheet.smallFontSheet);
	public static Sprite sm3 = new Sprite(8, 7, 4, SpriteSheet.smallFontSheet);
	public static Sprite sm4 = new Sprite(8, 8, 4, SpriteSheet.smallFontSheet);
	public static Sprite sm5 = new Sprite(8, 9, 4, SpriteSheet.smallFontSheet);
	public static Sprite sm6 = new Sprite(8, 10, 4, SpriteSheet.smallFontSheet);
	public static Sprite sm7 = new Sprite(8, 11, 4, SpriteSheet.smallFontSheet);
	public static Sprite sm8 = new Sprite(8, 12, 4, SpriteSheet.smallFontSheet);
	public static Sprite sm9 = new Sprite(8, 0, 5, SpriteSheet.smallFontSheet);
	public static Sprite sm0 = new Sprite(8, 1, 5, SpriteSheet.smallFontSheet);
	
	public static Sprite textBox = new Sprite(130,0,0,SpriteSheet.textBox);
	public static Sprite background = new Sprite(400,0,0,SpriteSheet.background);
	public static Sprite invSlot = new Sprite(400, 0, 0, SpriteSheet.invSlot);
	public static Sprite select = new Sprite(8, 0, 0, SpriteSheet.select);
	public static Sprite endSelect = new Sprite(8, 0, 0, SpriteSheet.endSelect);
	public static Sprite actionMenu = new Sprite(64, 0, 0, SpriteSheet.actionMenu);
	public static Sprite taco = new Sprite(16, 0, 0, SpriteSheet.taco);
	
	public static Sprite particle = new Sprite(4, 0, 0, SpriteSheet.particle);
	public static Sprite rain1 = new Sprite(16, 0, 0, SpriteSheet.rain);
	public static Sprite rain2 = new Sprite(16, 1, 0, SpriteSheet.rain);
	public static Sprite rain3 = new Sprite(16, 2, 0, SpriteSheet.rain);
	
	public static Sprite snow1 = new Sprite(16, 0, 0, SpriteSheet.snow);
	public static Sprite snow2 = new Sprite(16, 1, 0, SpriteSheet.snow);
	public static Sprite snow3 = new Sprite(16, 2, 0, SpriteSheet.snow);
	
	//////////////TOOL SPRITES////////////////////////////////////////////////////////////////////////////////////////////
	public static Sprite hoe = new Sprite(16, 0, 0, SpriteSheet.tools);
	public static Sprite wateringCan = new Sprite(16, 1, 0, SpriteSheet.tools);
	// 16x32 NEW PLANT SPRITES
	// Row 0
	public static Sprite sRice1 = new Sprite(16, 32, 0, 0, SpriteSheet.plant_test);
	public static Sprite sRice2 = new Sprite(16, 32, 1, 0, SpriteSheet.plant_test);
	public static Sprite sRice3 = new Sprite(16, 32, 2, 0, SpriteSheet.plant_test);
	public static Sprite sRice4 = new Sprite(16, 32, 3, 0, SpriteSheet.plant_test);
	public static Sprite sCabbage1 = new Sprite(16, 32, 4, 0, SpriteSheet.plant_test);
	public static Sprite sCabbage2 = new Sprite(16, 32, 5, 0, SpriteSheet.plant_test);
	public static Sprite sCabbage3 = new Sprite(16, 32, 6, 0, SpriteSheet.plant_test);
	public static Sprite sCabbage4 = new Sprite(16, 32, 7, 0, SpriteSheet.plant_test);
	public static Sprite sCorn1 = new Sprite(16, 32, 8, 0, SpriteSheet.plant_test);
	public static Sprite sCorn2 = new Sprite(16, 32, 9, 0, SpriteSheet.plant_test);
	public static Sprite sCorn3 = new Sprite(16, 32, 10, 0, SpriteSheet.plant_test);
	public static Sprite sCorn4 = new Sprite(16, 32, 11, 0, SpriteSheet.plant_test);
	public static Sprite sCarrot1 = new Sprite(16, 32, 12, 0, SpriteSheet.plant_test);
	public static Sprite sCarrot2 = new Sprite(16, 32, 13, 0, SpriteSheet.plant_test);
	public static Sprite sCarrot3 = new Sprite(16, 32, 14, 0, SpriteSheet.plant_test);
	public static Sprite sCarrot4 = new Sprite(16, 32, 15, 0, SpriteSheet.plant_test);

	// Row 1
	public static Sprite sRadish1 = new Sprite(16, 32, 0, 1, SpriteSheet.plant_test);
	public static Sprite sRadish2 = new Sprite(16, 32, 1, 1, SpriteSheet.plant_test);
	public static Sprite sRadish3 = new Sprite(16, 32, 2, 1, SpriteSheet.plant_test);
	public static Sprite sRadish4 = new Sprite(16, 32, 3, 1, SpriteSheet.plant_test);
	public static Sprite sTomato1 = new Sprite(16, 32, 4, 1, SpriteSheet.plant_test);
	public static Sprite sTomato2 = new Sprite(16, 32, 5, 1, SpriteSheet.plant_test);
	public static Sprite sTomato3 = new Sprite(16, 32, 6, 1, SpriteSheet.plant_test);
	public static Sprite sTomato4 = new Sprite(16, 32, 7, 1, SpriteSheet.plant_test);
	public static Sprite sPumpkin1 = new Sprite(16, 32, 8, 1, SpriteSheet.plant_test);
	public static Sprite sPumpkin2 = new Sprite(16, 32, 9, 1, SpriteSheet.plant_test);
	public static Sprite sPumpkin3 = new Sprite(16, 32, 10, 1, SpriteSheet.plant_test);
	public static Sprite sPumpkin4 = new Sprite(16, 32, 11, 1, SpriteSheet.plant_test);
	public static Sprite sEggplant1 = new Sprite(16, 32, 12, 1, SpriteSheet.plant_test);
	public static Sprite sEggplant2 = new Sprite(16, 32, 13, 1, SpriteSheet.plant_test);
	public static Sprite sEggplant3 = new Sprite(16, 32, 14, 1, SpriteSheet.plant_test);
	public static Sprite sEggplant4 = new Sprite(16, 32, 15, 1, SpriteSheet.plant_test);

	// Row 2
	public static Sprite sChili1 = new Sprite(16, 32, 0, 2, SpriteSheet.plant_test);
	public static Sprite sChili2 = new Sprite(16, 32, 1, 2, SpriteSheet.plant_test);
	public static Sprite sChili3 = new Sprite(16, 32, 2, 2, SpriteSheet.plant_test);
	public static Sprite sChili4 = new Sprite(16, 32, 3, 2, SpriteSheet.plant_test);
	public static Sprite sPepper1 = new Sprite(16, 32, 4, 2, SpriteSheet.plant_test);
	public static Sprite sPepper2 = new Sprite(16, 32, 5, 2, SpriteSheet.plant_test);
	public static Sprite sPepper3 = new Sprite(16, 32, 6, 2, SpriteSheet.plant_test);
	public static Sprite sPepper4 = new Sprite(16, 32, 7, 2, SpriteSheet.plant_test);

	// Row 3
	public static Sprite sSeedGlobal = new Sprite(16, 32, 0, 3, SpriteSheet.plant_test);
	public static Sprite sDead1 = new Sprite(16, 32, 1, 3, SpriteSheet.plant_test);
	public static Sprite sDead2 = new Sprite(16, 32, 2, 3, SpriteSheet.plant_test);

	public static Sprite tomatoFruit = new Sprite(16, 0, 0, SpriteSheet.fruit);
	public static Sprite cornFruit = new Sprite(16, 1, 0, SpriteSheet.fruit);
	
	
	//////////////////////////HOUSE TILE SPRITES///////////////////////////////////////////////////////////////////////
	public static Sprite houseRoof1 = new Sprite(16, 0, 7, SpriteSheet.tiles);
	public static Sprite houseRoof2 = new Sprite(16, 1, 7, SpriteSheet.tiles);
	public static Sprite houseRoof3 = new Sprite(16, 2, 7, SpriteSheet.tiles);
	public static Sprite houseRoof4 = new Sprite(16, 3, 7, SpriteSheet.tiles);
	public static Sprite houseRoof5 = new Sprite(16, 4, 7, SpriteSheet.tiles);
	public static Sprite houseRoof6 = new Sprite(16, 5, 7, SpriteSheet.tiles);
	public static Sprite houseRoof7 = new Sprite(16, 6, 7, SpriteSheet.tiles);
	public static Sprite houseBotRoof1 = new Sprite(16, 0, 8, SpriteSheet.tiles);
	public static Sprite houseBotRoof2 = new Sprite(16, 1, 8, SpriteSheet.tiles);
	public static Sprite houseBotRoof3 = new Sprite(16, 2, 8, SpriteSheet.tiles);
	public static Sprite houseBotRoof4 = new Sprite(16, 3, 8, SpriteSheet.tiles);
	public static Sprite houseBotRoof5 = new Sprite(16, 4, 8, SpriteSheet.tiles);
	public static Sprite houseBotRoof6 = new Sprite(16, 5, 8, SpriteSheet.tiles);
	public static Sprite houseBotRoof7 = new Sprite(16, 6, 8, SpriteSheet.tiles);
	public static Sprite houseTop1 = new Sprite(16, 0, 9, SpriteSheet.tiles);
	public static Sprite houseTop2 = new Sprite(16, 1, 9, SpriteSheet.tiles);
	public static Sprite houseTop3 = new Sprite(16, 2, 9, SpriteSheet.tiles);
	public static Sprite houseTop4 = new Sprite(16, 3, 9, SpriteSheet.tiles);
	public static Sprite houseTop5 = new Sprite(16, 4, 9, SpriteSheet.tiles);
	public static Sprite houseTop6 = new Sprite(16, 5, 9, SpriteSheet.tiles);
	public static Sprite houseTop7 = new Sprite(16, 6, 9, SpriteSheet.tiles);
	public static Sprite houseMid1 = new Sprite(16, 0, 10, SpriteSheet.tiles);
	public static Sprite houseMid2 = new Sprite(16, 1, 10, SpriteSheet.tiles);
	public static Sprite houseMid3 = new Sprite(16, 2, 10, SpriteSheet.tiles);
	public static Sprite houseMid4 = new Sprite(16, 3, 10, SpriteSheet.tiles);
	public static Sprite houseMid5 = new Sprite(16, 4, 10, SpriteSheet.tiles);
	public static Sprite houseMid6 = new Sprite(16, 5, 10, SpriteSheet.tiles);
	public static Sprite houseMid7 = new Sprite(16, 6, 10, SpriteSheet.tiles);
	public static Sprite houseBot1 = new Sprite(16, 0, 11, SpriteSheet.tiles);
	public static Sprite houseBot2 = new Sprite(16, 1, 11, SpriteSheet.tiles);
	public static Sprite houseBot3 = new Sprite(16, 2, 11, SpriteSheet.tiles);
	public static Sprite houseBot4 = new Sprite(16, 3, 11, SpriteSheet.tiles);
	public static Sprite houseBot5 = new Sprite(16, 4, 11, SpriteSheet.tiles);
	public static Sprite houseBot6 = new Sprite(16, 5, 11, SpriteSheet.tiles);
	public static Sprite houseBot7 = new Sprite(16, 6, 11, SpriteSheet.tiles);
	
	public static Sprite hoeAnimRight1 = new Sprite(32,0,0,SpriteSheet.animHoe);
	public static Sprite hoeAnimRight2 = new Sprite(32,1,0,SpriteSheet.animHoe);
	public static Sprite hoeAnimLeft1 = new Sprite(32,3,0,SpriteSheet.animHoe);
	public static Sprite hoeAnimLeft2 = new Sprite(32,2,0,SpriteSheet.animHoe);
	public static Sprite hoeAnimDown1 = new Sprite(32, 0, 1, SpriteSheet.animHoe);
	public static Sprite hoeAnimDown2 = new Sprite(32, 1, 1, SpriteSheet.animHoe);

	
	
		public Sprite(int[] pixels, int width, int height) {
		if (width == height) SIZE = width;
		else SIZE = -1;
		this.width = width;
		this.height = height;
		this.pixels = pixels;
		}
		
		
		protected Sprite(SpriteSheet sprite, int width, int height) {
			if (width == height) SIZE = width;
			else SIZE = -1;
			this.width = width;
			this.height = height;
			this.sheet = sheet;
		}
	//Sprite declaration for more detailed sprites requires a size and x and y coords on sheet and requires a sheet to pull it from.
	public Sprite(int size, int x, int y, SpriteSheet sheet) {
		SIZE = size; //Sprite size is the same as int that is passed
		this.width = size;
		this.height = size;
		pixels = new int[SIZE * SIZE]; //Create a pixel array the size of the sprite
		this.x = x * size; //x * size to go from pixel precision to tile precision
		//Ex. if x passed is 2 then the pixel needed is 32
		this.y = y * size; //y * size to go from pixel to tile precision
		this.sheet = sheet; //The spritesheet that our sprite is pulled from
		load();
	}

	public Sprite(int width, int height, int x, int y, SpriteSheet sheet) {
		SIZE = (width == height) ? width : -1;
		this.width = width;
		this.height = height;
		pixels = new int[width * height];
		this.x = x * width;
		this.y = y * height;
		this.sheet = sheet;
		load();
	}
	//Sprite declaration for void sprite only requires a size and a color
	public Sprite(int size, int color) {
		SIZE = size; //Sets sprite's size to the size passed in the method
		pixels = new int[SIZE * SIZE]; //Pixel array the size of the sprite
		setColor(color); //Take the color passed by the method and call the set color method to fill the array.
	}
	
	
	//Method cycles through all pixels within a sprite and sets them equal to color
	private void setColor(int color) {
		for (int i = 0; i < SIZE * SIZE; i++) {
			pixels[i] = color;
		}
	}
	
	//Method to load the sprite from the sheet
	private void load() {
		//Cycle through all pixels in the sprite
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				pixels[x + y * width] = sheet.pixels[(x + this.x) + (y + this.y) * sheet.SIZE]; //Sets the sprite's pixel array equal to
				//the sheets array for in specific area (1 tiles worth)
			}
		}
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
}
