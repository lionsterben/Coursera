# program template for Spaceship
import simplegui
import math
import random

# globals for user interface
WIDTH = 800
HEIGHT = 600
score = 0
lives = 3
time = 0
started = False
missile_group = set([])
explosion_group = set([])

class ImageInfo:
    def __init__(self, center, size, radius = 0, lifespan = None, animated = False):
        self.center = center
        self.size = size
        self.radius = radius
        if lifespan:
            self.lifespan = lifespan
        else:
            self.lifespan = float('inf')
        self.animated = animated

    def get_center(self):
        return self.center

    def get_size(self):
        return self.size

    def get_radius(self):
        return self.radius

    def get_lifespan(self):
        return self.lifespan

    def get_animated(self):
        return self.animated

    
# art assets created by Kim Lathrop, may be freely re-used in non-commercial projects, please credit Kim
    
# debris images - debris1_brown.png, debris2_brown.png, debris3_brown.png, debris4_brown.png
#                 debris1_blue.png, debris2_blue.png, debris3_blue.png, debris4_blue.png, debris_blend.png
debris_info = ImageInfo([320, 240], [640, 480])
debris_image = simplegui.load_image("http://commondatastorage.googleapis.com/codeskulptor-assets/lathrop/debris2_blue.png")

# nebula images - nebula_brown.png, nebula_blue.png
nebula_info = ImageInfo([400, 300], [800, 600])
nebula_image = simplegui.load_image("http://commondatastorage.googleapis.com/codeskulptor-assets/lathrop/nebula_blue.f2014.png")

# splash image
splash_info = ImageInfo([200, 150], [400, 300])
splash_image = simplegui.load_image("http://commondatastorage.googleapis.com/codeskulptor-assets/lathrop/splash.png")

# ship image
ship_info = ImageInfo([45, 45], [90, 90], 35)
ship_image = simplegui.load_image("http://commondatastorage.googleapis.com/codeskulptor-assets/lathrop/double_ship.png")

# missile image - shot1.png, shot2.png, shot3.png
missile_info = ImageInfo([5,5], [10, 10], 3, 50)
missile_image = simplegui.load_image("http://commondatastorage.googleapis.com/codeskulptor-assets/lathrop/shot2.png")

# asteroid images - asteroid_blue.png, asteroid_brown.png, asteroid_blend.png
asteroid_info = ImageInfo([45, 45], [90, 90], 40)
asteroid_image = simplegui.load_image("http://commondatastorage.googleapis.com/codeskulptor-assets/lathrop/asteroid_blue.png")

# animated explosion - explosion_orange.png, explosion_blue.png, explosion_blue2.png, explosion_alpha.png
explosion_info = ImageInfo([64, 64], [128, 128], 17, 24, True)
explosion_image = simplegui.load_image("http://commondatastorage.googleapis.com/codeskulptor-assets/lathrop/explosion_alpha.png")

# sound assets purchased from sounddogs.com, please do not redistribute
soundtrack = simplegui.load_sound("http://commondatastorage.googleapis.com/codeskulptor-assets/sounddogs/soundtrack.mp3")
missile_sound = simplegui.load_sound("http://commondatastorage.googleapis.com/codeskulptor-assets/sounddogs/missile.mp3")
missile_sound.set_volume(.5)
ship_thrust_sound = simplegui.load_sound("http://commondatastorage.googleapis.com/codeskulptor-assets/sounddogs/thrust.mp3")
explosion_sound = simplegui.load_sound("http://commondatastorage.googleapis.com/codeskulptor-assets/sounddogs/explosion.mp3")

# alternative upbeat soundtrack by composer and former IIPP student Emiel Stopler
# please do not redistribute without permission from Emiel at http://www.filmcomposer.nl
#soundtrack = simplegui.load_sound("https://storage.googleapis.com/codeskulptor-assets/ricerocks_theme.mp3")
soundtrack.play()
# helper functions to handle transformations
def angle_to_vector(ang):
    return [math.cos(ang), math.sin(ang)]

def dist(p,q):
    return math.sqrt((p[0] - q[0]) ** 2+(p[1] - q[1]) ** 2)


# Ship class
class Ship:
    def __init__(self, pos, vel, angle, image, info):
        self.pos = [pos[0],pos[1]]
        self.vel = [vel[0],vel[1]]
        self.thrust = False
        self.shoot = False
        self.angle = angle
        self.angle_vel = 0
        self.image = image
        self.image_center = info.get_center()
        self.image_size = info.get_size()
        self.radius = info.get_radius()
        
    def draw(self,canvas):
        if not self.thrust:
            canvas.draw_image(self.image, self.image_center, self.image_size,self.pos,self.image_size,self.angle)
        elif self.thrust:
            canvas.draw_image(self.image, [135,45], self.image_size,self.pos,self.image_size,self.angle)
            
    def update(self):
        forward = angle_to_vector(self.angle)
        c = 0.01
        self.pos[0] += self.vel[0]
        self.pos[1] += self.vel[1]
        self.pos[0] = self.pos[0]%WIDTH
        self.pos[1] = self.pos[1]%HEIGHT
        self.vel[0] *= (1-c)
        self.vel[1] *= (1-c)
        if self.thrust:
            self.vel[0] += forward[0]*0.1
            self.vel[1] += forward[1]*0.1
        
        self.angle += self.angle_vel  

    def left(self):
        self.angle_vel = -0.1
    
    def right(self):
        self.angle_vel = 0.1
    
    def up(self):
        self.thrust = True
        ship_thrust_sound.play()
    
    def rollback(self):
        self.angle_vel = 0
        
    def push_up(self):
        self.thrust = False
        ship_thrust_sound.rewind() 
    
    def oneshoot(self):
        forward = angle_to_vector(self.angle)
        missile_vel = [0,0]
        missile_pos = [self.pos[0] + forward[0]*self.radius,self.pos[1] + forward[1]*self.radius]
        missile_vel[0] = self.vel[0] + 3*forward[0]
        missile_vel[1] = self.vel[1] + 3*forward[1]
        
        missile_pos[0] += missile_vel[0]
        missile_pos[1] += missile_vel[1]
        
        a_missile = Sprite(missile_pos, missile_vel, 0, 0, missile_image, missile_info,missile_sound)
        missile_group.add(a_missile)
    def get_position(self):
        return self.pos
    
    def get_radius(self):
        return self.radius
    
        
def keydown(key):
    if key == simplegui.KEY_MAP["left"]:
        my_ship.left()
    elif key == simplegui.KEY_MAP["right"]:
        my_ship.right()
    elif key == simplegui.KEY_MAP["up"]:
        my_ship.up()
    elif key == simplegui.KEY_MAP["space"]:
        my_ship.oneshoot()

        
        

def keyup(key):
    if key == simplegui.KEY_MAP["left"] or key == simplegui.KEY_MAP["right"] :
        my_ship.rollback()
    elif key == simplegui.KEY_MAP["up"]:
        my_ship.push_up()
        
            
            
            


        
        
# Sprite class
class Sprite:
    def __init__(self, pos, vel, ang, ang_vel, image, info, sound = None):
        self.pos = [pos[0],pos[1]]
        self.vel = [vel[0],vel[1]]
        self.angle = ang
        self.angle_vel = ang_vel
        self.image = image
        self.image_center = info.get_center()
        self.image_size = info.get_size()
        self.radius = info.get_radius()
        self.lifespan = info.get_lifespan()
        self.animated = info.get_animated()
        self.age = 0
        if sound:
            sound.rewind()
            sound.play()
   
    def draw(self, canvas):
        global time
        if self.animated:
            row = time%24
            canvas.draw_image(self.image, 
                    [self.image_center[0] + row * self.image_size[0], 
                     self.image_center[1]], 
                     self.image_size, self.pos,self.image_size,self.angle)
            time += 1
        else:
            canvas.draw_image(self.image, self.image_center, self.image_size,self.pos,self.image_size,self.angle)
            
            
        
    
    def update(self):
            self.angle += self.angle_vel
            self.pos[0] += self.vel[0]
            self.pos[1] += self.vel[1]
            self.pos[0] = self.pos[0]%WIDTH
            self.pos[1] = self.pos[1]%HEIGHT
            self.age += 1
            if self.age >= self.lifespan:
                return True
            else:
                return False
                
    
    def get_position(self):
        return self.pos
    
    def get_radius(self):
        return self.radius
    
    def collide(self,other_sprite):
        other_position = other_sprite.get_position()
        other_radius = other_sprite.get_radius()
        sum_radius = self.radius + other_radius
        if dist(self.pos,other_position) <= sum_radius:
            return True
        else:
            return False
        
            
def click(pos):
    global started,lives,score
    lives = 3
    score = 0
    center = [WIDTH / 2, HEIGHT / 2]
    size = splash_info.get_size()
    inwidth = (center[0] - size[0] / 2) < pos[0] < (center[0] + size[0] / 2)
    inheight = (center[1] - size[1] / 2) < pos[1] < (center[1] + size[1] / 2)
    if (not started) and inwidth and inheight:
        started = True
            
           
         
         
         
               

def draw(canvas):
    global time,started,lives,rock_group,missile_group,score
    
    # animiate background
    time += 1
    wtime = (time / 4) % WIDTH
    center = debris_info.get_center()
    size = debris_info.get_size()
    canvas.draw_image(nebula_image, nebula_info.get_center(), nebula_info.get_size(), [WIDTH / 2, HEIGHT / 2], [WIDTH, HEIGHT])
    canvas.draw_image(debris_image, center, size, (wtime - WIDTH / 2, HEIGHT / 2), (WIDTH, HEIGHT))
    canvas.draw_image(debris_image, center, size, (wtime + WIDTH / 2, HEIGHT / 2), (WIDTH, HEIGHT))

    # draw ship and sprites
    my_ship.draw(canvas)
    
    
    
    # update ship and sprites
    my_ship.update()

    if group_group_collide(rock_group,missile_group):
        score += 1

    if group_collide(rock_group,my_ship):
        lives -= 1

    process_sprite_group(rock_group,canvas)
    process_sprite_group(missile_group,canvas)
    process_sprite_group(explosion_group,canvas)
    
    
    canvas.draw_text("Score",[600,80], 30, "White")
    canvas.draw_text("Lives",[100,80], 30, "White")
    canvas.draw_text(str(score),[630,130], 30, "White")
    canvas.draw_text(str(lives),[130,130], 30, "White")
    
    if not started:
        canvas.draw_image(splash_image, splash_info.get_center(), 
                          splash_info.get_size(), [WIDTH / 2, HEIGHT / 2], 
                          splash_info.get_size())
        
    if lives == 0:
        rock_group = set([])
        started = False
        soundtrack.rewind()
        soundtrack.play()
    


# timer handler that spawns a rock    
def rock_spawner():
    rock_pos = [random.randrange(0, WIDTH), random.randrange(0, HEIGHT)]
    rock_vel = [random.random() * .6 - .3, random.random() * .6 - .3]
    rock_angle_val = random.random() * .2 - .1
    return Sprite(rock_pos, rock_vel, 0, rock_angle_val, asteroid_image, asteroid_info)

#draw a set canvas
def process_sprite_group(a_set,canvas):
    for element in list(a_set):
        if element.update():
            a_set.remove(element)
        element.update()
        element.draw(canvas) 

def group_collide(group,other_object):
    n = 0
    for g in list(group):
        if g.collide(other_object):
            g_pos = g.get_position()
            a_explosion = Sprite(g_pos,[0,0],0,0,explosion_image, explosion_info, explosion_sound)
            explosion_group.add(a_explosion)
            group.remove(g)
            n = 1
    if n == 1:
        return True
    else:
        return False
    

def group_group_collide(set_1,set_2):
    num = 0
    for element in list(set_1):
        if group_collide(set_2,element):
            num += 1
            set_1.discard(element)    
    return num


# initialize frame
frame = simplegui.create_frame("Asteroids", WIDTH, HEIGHT)

# initialize ship and two sprites
my_ship = Ship([WIDTH / 2, HEIGHT / 2], [0, 0], 0, ship_image, ship_info)

#define rock
rock_group = set([])
def one_rock():
    global rock_group
    if len(rock_group) < 12 and started :
        a_rock = rock_spawner()
        rock_pos = a_rock.get_position()
        ship_pos = my_ship.get_position()
        rock_radius = a_rock.get_radius()
        ship_radius = my_ship.get_radius()
        if dist(rock_pos,ship_pos) > (rock_radius + ship_radius):
            rock_group.add(a_rock)
    


    
    
    
    
# register handlers
frame.set_draw_handler(draw)
frame.set_keydown_handler(keydown)
frame.set_keyup_handler(keyup)
frame.set_mouseclick_handler(click)



timer = simplegui.create_timer(1000.0, one_rock)


    

# get things rolling
timer.start()
frame.start()